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
import com.increff.employee.model.SalesReportData;
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
    public List<SalesReportData> get(SalesReportForm data) throws ApiException {
        logger.info("Inside Sales Report");
        Map<String, Integer> quantity = new HashMap<String, Integer>();
        Map<String, Double> revenue = new HashMap<String, Double>();


        if (data.startDate.equals("NaN")) {
            data.startDate = "0000000000000";
        }
        if (data.endDate.equals("NaN")) {
            data.endDate = "9999999999999";
        }
        logger.info("Start Date: " + data.startDate);
        logger.info("End Date: " + data.endDate);
        Date startDate = new Date(Long.parseLong(data.startDate));
        Date endDate = new Date(Long.parseLong(data.endDate) + (1000 * 60 * 60 * 24) - 1);
        if (startDate.compareTo(endDate) > 0) {
            throw new ApiException("Start date should be less than end date!");
        }
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
            logger.info("OrderId: " + orderPojo.getId());
            List<OrderItemPojo> temps = orderItemService.getByOrderId(orderPojo.getId());
            for (OrderItemPojo temp : temps) {
                Integer bcId =
                        productService.getByBarcode(temp.getProductBarcode()).getBrand_category();
                logger.info("\tOrderItemId: " + temp.getId() + ", Brand_catID: " + bcId);
                if (brandCategoryIds.contains(bcId)) {
                    String category = brandService.get(bcId).getCategory();
                    quantity.put(category, quantity.getOrDefault(category, 0) + temp.getQuantity());
                    revenue.put(category,
                            revenue.getOrDefault(category, 0.0) + temp.getSellingPrice());
                    logger.info("\tOrderItemIdStored: " + temp.getId() + ", Category: " + category);
                }
            }
        }

        List<SalesReportData> salesReportDtos = new ArrayList<SalesReportData>();
        quantity.forEach((k, v) -> salesReportDtos.add(new SalesReportData(k, v, revenue.get(k))));

        return salesReportDtos;
        // List<SalesPojo> inventoryPojos = inventoryService.getAll();

    }



}
