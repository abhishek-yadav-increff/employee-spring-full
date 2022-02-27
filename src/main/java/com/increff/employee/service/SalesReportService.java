package com.increff.employee.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    public List<SalesReportData> get(SalesReportForm data, Date startDate, Date endDate,
            Set<Integer> brandCategoryIds) throws ApiException {
        logger.info("Inside Sales Report");
        Map<String, Integer> quantity = new HashMap<String, Integer>();
        Map<String, Double> revenue = new HashMap<String, Double>();

        if (startDate.compareTo(endDate) > 0) {
            throw new ApiException("Start date should be less than end date!");
        }

        List<OrderPojo> orderPojos = orderService.getByTime(startDate.getTime(), endDate.getTime());
        for (OrderPojo orderPojo : orderPojos) {
            List<OrderItemPojo> temps = orderItemService.getByOrderId(orderPojo.getId());
            for (OrderItemPojo temp : temps) {
                Integer bcId =
                        productService.getByBarcode(temp.getProductBarcode()).getBrand_category();
                if (brandCategoryIds.contains(bcId)) {
                    String category = brandService.get(bcId).getCategory();
                    quantity.put(category, quantity.getOrDefault(category, 0) + temp.getQuantity());
                    revenue.put(category,
                            revenue.getOrDefault(category, 0.0) + temp.getSellingPrice());
                }
            }
        }

        List<SalesReportData> salesReportDtos = new ArrayList<SalesReportData>();
        quantity.forEach((k, v) -> salesReportDtos.add(new SalesReportData(k, v, revenue.get(k))));

        return salesReportDtos;
    }


}
