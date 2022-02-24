package com.increff.employee.model;


// @XmlRootElement(name = "OrderItemForm")
// @XmlAccessorType(XmlAccessType.FIELD)
public class OrderItemForm {
    private Integer id;

    private Integer orderId;
    private String productBarcode;
    private Integer quantity;
    private Double sellingPrice;

    public Integer getId() {
        return id;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    public void setId(Integer id) {
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
