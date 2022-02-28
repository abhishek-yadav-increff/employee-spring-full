package com.increff.employee.util;

import com.increff.employee.model.ProductForm;

/**
 * ProductTestUtil
 */
public class ProductTestUtil {

    public static ProductForm getProductForm(String barcode, String brand, String category,
            String name, Double mrp) {
        // return new ProductForm(barcode, brand, category, name, mrp);
        ProductForm productForm = new ProductForm();
        productForm.setBarcode(barcode);
        productForm.setBrand(brand);
        productForm.setCategory(category);
        productForm.setMrp(mrp);
        productForm.setName(name);
        return productForm;
    }
}
