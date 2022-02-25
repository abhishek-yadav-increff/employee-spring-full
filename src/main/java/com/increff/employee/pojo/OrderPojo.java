package com.increff.employee.pojo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "port_gen", sequenceName = "port_gen", initialValue = 1000,
        allocationSize = 1)
public class OrderPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "port_gen")
    private Integer id;

    private Long timeMilis;

    private Integer isCompleted;

    @Column(precision = 2)
    private Double cost;

    public OrderPojo() {
        this.timeMilis = new Date().getTime();
        this.isCompleted = 0;
        this.cost = 0.0;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getComplete() {
        return isCompleted;
    }

    public void setComplete(Integer complete) {
        this.isCompleted = complete;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTime() {
        return timeMilis;
    }

    public void setTime(Long time) {
        this.timeMilis = time;
    }


}
