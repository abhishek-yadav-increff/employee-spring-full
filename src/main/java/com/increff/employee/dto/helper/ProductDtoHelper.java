package com.increff.employee.dto.helper;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.ProductPojo;

/**
 * ProductDtoHelper
 */
public class ProductDtoHelper {


    public static ProductData convert(ProductPojo productPojo, String brand, String category) {
        ProductData productData = new ProductData();
        productData.setBrand(brand);
        productData.setCategory(category);
        productData.setBarcode(productPojo.getBarcode());
        productData.setId(productPojo.getId());
        productData.setName(productPojo.getName());
        productData.setMrp(productPojo.getMrp());
        return productData;
    }

    public static ProductPojo convert(ProductForm f, Integer id) {
        ProductPojo p = new ProductPojo();
        p.setBrand_category(id);
        p.setBarcode(f.getBarcode());
        p.setName(f.getName());
        p.setMrp(f.getMrp());
        return p;
    }

    public static ProductForm normalize(ProductForm form) {
        form.setBrand(CommonsHelper.normalize(form.getBrand()));
        form.setCategory(CommonsHelper.normalize(form.getCategory()));
        form.setBarcode(CommonsHelper.normalize(form.getBarcode()));
        form.setName(CommonsHelper.normalize(form.getName()));
        form.setMrp(CommonsHelper.normalize(form.getMrp()));
        return form;
    }


}
