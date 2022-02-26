package com.increff.employee.model;


import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrderItemForms {

    List<OrderItemXmlForm> orderItemFormData;

    @XmlElement
    public List<OrderItemXmlForm> getOrderItemFormData() {
        return orderItemFormData;
    }

    public void setOrderItemFormData(List<OrderItemXmlForm> orderItemFormData) {
        this.orderItemFormData = orderItemFormData;
    }


}
