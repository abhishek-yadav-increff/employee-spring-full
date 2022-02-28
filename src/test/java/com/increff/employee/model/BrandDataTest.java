package com.increff.employee.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * BrandDataTest
 */
public class BrandDataTest {

    @Test
    public void testBrandData() {
        BrandData brandData = new BrandData();
        String brand = "cadbury";
        String category = "chocolate";
        Integer id = 1;
        brandData.setId(id);
        brandData.setBrand(brand);
        brandData.setCategory(category);
        assertEquals(brand, brandData.getBrand());
        assertEquals(category, brandData.getCategory());
        assertEquals(id, brandData.getId());
    }
}
