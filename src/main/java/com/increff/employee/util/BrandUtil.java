package com.increff.employee.util;

import com.increff.employee.model.BrandForm;

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
}
