package com.increff.employee.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SalesReportForm {
    @JsonProperty("brand")
    public String brand;
    @JsonProperty("category")
    public String category;
    @JsonProperty("startDate")
    public String startDate;
    @JsonProperty("endDate")
    public String endDate;


    public String toString() {
        return "Brand: " + brand + ", Category: " + category + ", Start Date: " + startDate
                + ", End Date: " + endDate + "\n";
    }
}
