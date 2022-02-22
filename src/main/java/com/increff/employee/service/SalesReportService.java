package com.increff.employee.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.transaction.Transactional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.increff.employee.dto.SalesReportDto;
import com.increff.employee.model.SalesReportForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
// import com.increff.employee.pojo.SalesPojo;

@Service
public class SalesReportService {
    private static Logger logger = LogManager.getLogger(SalesReportService.class);

    @Autowired
    private BrandService brandService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderItemService orderItemService;

    @Transactional(rollbackOn = ApiException.class)
    public List<SalesReportDto> get(SalesReportForm data) throws ApiException {
        logger.info("Inside Sales Report");
        Map<String, Integer> quantity = new HashMap<String, Integer>();
        Map<String, Double> revenue = new HashMap<String, Double>();


        Date startDate = new Date(Long.parseLong(data.startDate));
        Date endDate = new Date(Long.parseLong(data.endDate) + (1000 * 60 * 60 * 24) - 1);
        logger.info("Start Date: " + startDate.toString());
        logger.info("End Date: " + endDate.toString());

        Set<Integer> brandCategoryIds = new HashSet<Integer>();
        List<BrandPojo> brandPojos =
                brandService.getListByBrandAndCategory(data.brand, data.category);
        for (BrandPojo brandPojo : brandPojos) {
            brandCategoryIds.add(brandPojo.getId());
        }
        for (Integer i : brandCategoryIds) {
            logger.info("brandCategoryIds: " + i);
        }

        List<OrderPojo> orderPojos = orderService.getByTime(startDate.getTime(), endDate.getTime());
        logger.info("Size or orders in that duration: " + orderPojos.size());
        for (OrderPojo orderPojo : orderPojos) {
            if (orderPojo.getComplete() == 1) {
                logger.info("OrderId: " + orderPojo.getId());
                List<OrderItemPojo> temps = orderItemService.getByOrderId(orderPojo.getId());
                for (OrderItemPojo temp : temps) {
                    Integer bcId = productService.getByBarcode(temp.getProductBarcode())
                            .getBrand_category();
                    logger.info("\tOrderItemId: " + temp.getId() + ", Brand_catID: " + bcId);
                    if (brandCategoryIds.contains(bcId)) {
                        String category = brandService.get(bcId).getCategory();
                        quantity.put(category,
                                quantity.getOrDefault(category, 0) + temp.getQuantity());
                        revenue.put(category,
                                revenue.getOrDefault(category, 0.0) + temp.getSellingPrice());
                        logger.info(
                                "\tOrderItemIdStored: " + temp.getId() + ", Category: " + category);
                    }
                }
            }
        }

        List<SalesReportDto> salesReportDtos = new ArrayList<SalesReportDto>();
        quantity.forEach((k, v) -> salesReportDtos.add(new SalesReportDto(k, v, revenue.get(k))));

        return salesReportDtos;
        // List<SalesPojo> inventoryPojos = inventoryService.getAll();

    }

    // public static List<SalesReportDto> convert(List<SalesPojo> inventoryPojos) {
    // List<SalesReportDto> inventoryReportDtos = new ArrayList<SalesReportDto>();
    // for (SalesPojo inventoryPojo : inventoryPojos) {
    // inventoryReportDtos.add(convert(inventoryPojo));
    // }
    // return inventoryReportDtos;
    // }

    // public static SalesReportDto convert(SalesPojo inventoryPojo) {
    // SalesReportDto p = new SalesReportDto();
    // p.setBarcode(inventoryPojo.getBarcode());
    // p.setQuantity(inventoryPojo.getQuantity());
    // return p;
    // }


}
