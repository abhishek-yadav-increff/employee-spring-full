package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;
import com.increff.employee.dto.helper.CommonsHelper;
import com.increff.employee.dto.helper.ProductDtoHelper;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ProductDto
 */
@Service
public class ProductDto {

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    public void add(ProductForm form) throws ApiException {
        form = ProductDtoHelper.normalize(form);
        BrandPojo brandPojo =
                brandService.getByBrandAndCategory(form.getBrand(), form.getCategory());
        ProductPojo p = ProductDtoHelper.convert(form, brandPojo.getId());
        productService.add(p);
    }

    public ProductData get(int id) throws ApiException {
        ProductPojo p = productService.get(id);
        BrandPojo brandPojo = brandService.get(p.getBrand_category());
        return ProductDtoHelper.convert(p, brandPojo.getBrand(), brandPojo.getCategory());
    }

    public ProductData getByBarcode(String barcode) throws ApiException {
        barcode = CommonsHelper.normalize(barcode);
        ProductPojo p = productService.getByBarcode(barcode);
        BrandPojo brandPojo = brandService.get(p.getBrand_category());
        return ProductDtoHelper.convert(p, brandPojo.getBrand(), brandPojo.getCategory());
    }

    public List<ProductData> getAll() throws ApiException {
        List<ProductPojo> productPojos = productService.getAll();
        List<ProductData> productDatas = new ArrayList<ProductData>();
        for (ProductPojo p : productPojos) {
            BrandPojo brandPojo = brandService.get(p.getBrand_category());
            productDatas.add(
                    ProductDtoHelper.convert(p, brandPojo.getBrand(), brandPojo.getCategory()));
        }
        return productDatas;
    }

    public void update(int id, ProductForm form) throws ApiException {
        form = ProductDtoHelper.normalize(form);
        BrandPojo brandPojo =
                brandService.getByBrandAndCategory(form.getBrand(), form.getCategory());
        ProductPojo p = ProductDtoHelper.convert(form, brandPojo.getId());
        productService.update(id, p);
    }

}
