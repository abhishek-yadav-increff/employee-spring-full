package com.increff.employee.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.increff.employee.id.UUIDGenerator;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;

@Entity
public class ProductPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @GeneratorType(type = UUIDGenerator.class, when = GenerationTime.INSERT)
    private String barcode;

    private Integer brandCategoryId;

    private String name;

    @Column(scale = 2)
    private Double mrp;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getBrand_category() {
        return this.brandCategoryId;
    }

    public void setBrand_category(Integer brand_category) {
        this.brandCategoryId = brand_category;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMrp() {
        return this.mrp;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }

}
