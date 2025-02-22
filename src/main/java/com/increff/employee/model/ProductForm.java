package com.increff.employee.model;

public class ProductForm {

    private String barcode;
    private String brand;
    private String category;
    private String name;
    private Double mrp;

    public ProductForm(String barcode, String brand, String category, String name, Double mrp) {
        this.barcode = barcode;
        this.brand = brand;
        this.category = category;
        this.name = name;
        this.mrp = mrp;
    }

    public ProductForm() {}

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMrp() {
        return mrp;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }

}
