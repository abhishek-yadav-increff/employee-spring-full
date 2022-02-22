package com.increff.employee.model;

public class OrderForm {

    private Integer id;
    private Long time;
    private Integer complete;
    private Double cost;

    public Integer getId() {
        return id;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getComplete() {
        return complete;
    }

    public void setComplete(Integer complete) {
        this.complete = complete;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long date) {
        this.time = date;
    }

}
