package com.increff.employee.model;


import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderItemForms {

    List<OrderItemForm> orderItemFormData;

    @XmlElement
    public List<OrderItemForm> getOrderItemFormData() {
        return orderItemFormData;
    }

    public void setOrderItemFormData(List<OrderItemForm> orderItemFormData) {
        this.orderItemFormData = orderItemFormData;
    }


}
