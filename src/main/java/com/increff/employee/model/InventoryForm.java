package com.increff.employee.model;

public class InventoryForm {

    private String barcode;
    private Integer quantity;
    private String name;


    public Integer getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String toString() {
        return getBarcode() + " " + getName() + " " + getQuantity();
    }
}
