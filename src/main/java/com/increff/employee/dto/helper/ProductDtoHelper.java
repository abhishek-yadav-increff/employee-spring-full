package com.increff.employee.dto.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.ProductPojo;

/**
 * ProductDtoHelper
 */
public class ProductDtoHelper {

    public static ProductPojo normalize(ProductPojo productPojo) {
        productPojo.setName(CommonsHelper.normalize(productPojo.getName()));
        BigDecimal bigDecimal =
                new BigDecimal(productPojo.getMrp()).setScale(2, RoundingMode.HALF_DOWN);
        Double scaledDouble = bigDecimal.doubleValue();
        productPojo.setMrp(scaledDouble);
        return productPojo;
    }

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
}
