package com.increff.employee.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "BrandAndCategory", columnNames = {"brand", "category"})})
public class BrandPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String brand;
    private String category;
    // @OneToMany(targetEntity = ProductPojo.class, mappedBy = "brand_category")
    // private Set<ProductPojo> products;
    // @OneToMany(mappedBy = "brandPojo", cascade = CascadeType.ALL)
    // Set<ProductPojo> productPojos = new HashSet<ProductPojo>();
    // @OneToMany(mappedBy = "brandpojo")
    // private Set<ProductPojo> items;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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



}
