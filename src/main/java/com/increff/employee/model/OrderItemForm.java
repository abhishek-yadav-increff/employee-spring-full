package com.increff.employee.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "orderitem")
// @XmlAccessorType(XmlAccessType.FIELD)
public class OrderItemForm {
    private Integer id;
    private Integer orderId;
    private String productBarcode;
    private String mrp;
    private Integer quantity;
    private String sellingPrice;
    private String name;

    @XmlTransient
    public Integer getId() {
        return id;
    }

    @XmlElement
    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @XmlElement
    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    @XmlElement
    public String getProductBarcode() {
        return productBarcode;
    }

    @XmlTransient
    public Integer getOrderId() {
        return orderId;
    }

    @XmlElement
    public Integer getQuantity() {
        return quantity;
    }



    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }



    public void setName(String name) {
        this.name = name;
    }

}
