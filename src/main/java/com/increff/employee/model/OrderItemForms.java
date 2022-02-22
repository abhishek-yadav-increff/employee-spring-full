package com.increff.employee.model;


import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

// @XmlRootElement(name = "OrderItemForms")
public class OrderItemForms {
    List<OrderItemForm> orderItemForms;

    public List<OrderItemForm> getOrderItemForms() {
        return orderItemForms;
    }

    public void setOrderItemForms(List<OrderItemForm> orderItemForms) {
        this.orderItemForms = orderItemForms;
    }
}
