package com.increff.employee.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "order_item_id_generator", sequenceName = "order_item_id_generator",
        initialValue = 100000, allocationSize = 1)
public class OrderItemPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "order_item_id_generator")
    private int id;

    private Integer orderId;
    private String productBarcode;
    private Integer quantity;

    @Column(scale = 2)
    private Double sellingPrice;

    public int getId() {
        return id;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }



}
