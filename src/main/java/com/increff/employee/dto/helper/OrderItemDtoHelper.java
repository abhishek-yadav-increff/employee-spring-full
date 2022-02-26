package com.increff.employee.dto.helper;

import com.increff.employee.model.OrderItemForm;
import com.increff.employee.model.OrderItemXmlForm;
import com.increff.employee.pojo.OrderItemPojo;

/**
 * OrderItemDtoHelper
 */
public class OrderItemDtoHelper {

    public static OrderItemPojo convert(OrderItemForm f) {
        OrderItemPojo p = new OrderItemPojo();
        p.setOrderId(f.getOrderId());
        p.setQuantity(f.getQuantity());
        p.setProductBarcode(f.getProductBarcode());
        return p;
    }

    public static OrderItemPojo normalize(OrderItemPojo p) {
        p.setSellingPrice(CommonsHelper.normalize(p.getSellingPrice()));
        return p;
    }

    public static OrderItemXmlForm convert(OrderItemPojo p, Double mrp, String name) {
        OrderItemXmlForm d = new OrderItemXmlForm();
        d.setId(p.getId());
        d.setOrderId(p.getOrderId());
        d.setQuantity(p.getQuantity());
        d.setProductBarcode(p.getProductBarcode());
        d.setMrp(String.format("%.2f", mrp));
        d.setSellingPrice(String.format("%.2f", p.getSellingPrice()));
        d.setName(name);
        return d;
    }

    public static OrderItemForm convertForm(OrderItemPojo p, Double mrp, String name) {
        OrderItemForm d = new OrderItemForm();
        d.setId(p.getId());
        d.setOrderId(p.getOrderId());
        d.setQuantity(p.getQuantity());
        d.setProductBarcode(p.getProductBarcode());
        d.setMrp(String.format("%.2f", mrp));
        d.setSellingPrice(String.format("%.2f", p.getSellingPrice()));
        d.setName(name);
        return d;
    }
}
