package com.increff.employee.model;

public class InventoryForm {

    private String barcode;
    private Integer quantity;


    public Integer getQuantity() {
        return quantity;
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


}
