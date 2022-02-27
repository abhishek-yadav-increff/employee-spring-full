package com.increff.employee.dto.helper;

import com.increff.employee.model.OrderForm;
import com.increff.employee.pojo.OrderPojo;

/**
 * OrderDtoHelper
 */
public class OrderDtoHelper {

    public static OrderForm convert(OrderPojo p) {
        OrderForm d = new OrderForm();
        d.setTime(p.getTime());
        d.setId(p.getId());
        d.setComplete(p.getComplete());
        d.setCost(CommonsHelper.normalize(p.getCost()));
        return d;
    }

    public static OrderPojo convert(OrderForm f) {
        OrderPojo p = new OrderPojo();
        p.setId(f.getId());
        p.setComplete(f.getComplete());
        p.setCost(f.getCost());
        return p;
    }

}
