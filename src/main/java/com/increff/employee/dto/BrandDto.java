package com.increff.employee.dto;

import java.util.List;
import com.increff.employee.dto.helper.BrandDtoHelper;
import com.increff.employee.dto.helper.CommonsHelper;
import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BrandDto
 */
@Service
public class BrandDto {

    @Autowired
    private BrandService brandService;

    public void add(BrandForm brandForm) throws ApiException {
        BrandPojo brandPojo = BrandDtoHelper.convert(brandForm);
        brandPojo = BrandDtoHelper.normalize(brandPojo);
        brandService.add(brandPojo);
    }

    public BrandData get(int id) throws ApiException {
        BrandPojo brandPojo = brandService.get(id);
        return BrandDtoHelper.convert(brandPojo);
    }

    public List<BrandData> getAll() throws ApiException {
        List<BrandPojo> brandPojos = brandService.getAll();
        return BrandDtoHelper.convert(brandPojos);
    }

    public List<BrandData> getByCategory(String category) throws ApiException {
        category = CommonsHelper.normalize(category);
        List<BrandPojo> brandPojos = brandService.getByCategory(category);
        return BrandDtoHelper.convert(brandPojos);
    }

    public List<BrandData> getByBrand(String brand) throws ApiException {
        brand = CommonsHelper.normalize(brand);
        List<BrandPojo> brandPojos = brandService.getByBrand(brand);
        return BrandDtoHelper.convert(brandPojos);
    }

    public List<BrandData> getListByBrandAndCategory(String brand, String category)
            throws ApiException {
        category = CommonsHelper.normalize(category);
        brand = CommonsHelper.normalize(brand);
        List<BrandPojo> brandPojos = brandService.getListByBrandAndCategory(brand, category);
        return BrandDtoHelper.convert(brandPojos);
    }

    public void update(int id, BrandForm f) throws ApiException {
        BrandPojo p = BrandDtoHelper.convert(f);
        p = BrandDtoHelper.normalize(p);
        brandService.update(id, p);
    }
}
