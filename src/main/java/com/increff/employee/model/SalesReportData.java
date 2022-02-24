package com.increff.employee.model;

public class SalesReportData {

    private String category;
    private Integer quantity;
    private Double revenue;

    public SalesReportData(String category, Integer quantity, Double revenue) {
        this.category = category;
        this.quantity = quantity;
        this.revenue = revenue;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public String toString() {
        return "Category: " + getCategory() + ", Quantity: " + getQuantity() + ", Revenue: "
                + getRevenue().toString() + "\n";
    }
}
