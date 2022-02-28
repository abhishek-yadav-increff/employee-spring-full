package com.increff.employee.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * BrandFormTest
 */
public class BrandFormTest {

    @Test
    public void testBrandFormForm() {
        BrandForm brandFormForm = new BrandForm();
        String brand = "cadbury";
        String category = "chocolate";
        brandFormForm.setBrand(brand);
        brandFormForm.setCategory(category);
        assertEquals(brand, brandFormForm.getBrand());
        assertEquals(category, brandFormForm.getCategory());
    }
}
