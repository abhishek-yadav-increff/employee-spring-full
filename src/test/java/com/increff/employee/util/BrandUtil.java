package com.increff.employee.util;

import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;

/**
 * BrandUtil
 */
public class BrandUtil {

    public static BrandForm getBrandFormDto(String brand, String category) {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand(brand);
        brandForm.setCategory(category);
        return brandForm;
    }

    public static BrandPojo getBrandPojo(String brand, String category) {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brand);
        brandPojo.setCategory(category);
        return brandPojo;
    }
}
