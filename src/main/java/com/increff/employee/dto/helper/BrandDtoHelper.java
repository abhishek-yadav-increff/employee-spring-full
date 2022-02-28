package com.increff.employee.dto.helper;

import java.util.ArrayList;
import java.util.List;
import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;

/**
 * BrandDtoHelper
 */
public class BrandDtoHelper {

    public static BrandData convert(BrandPojo brandPojo) {
        BrandData brandData = new BrandData();
        brandData.setBrand(brandPojo.getBrand());
        brandData.setCategory(brandPojo.getCategory());
        brandData.setId(brandPojo.getId());
        return brandData;
    }

    public static BrandPojo convert(BrandForm brandForm) {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brandForm.getBrand());
        brandPojo.setCategory(brandForm.getCategory());
        return brandPojo;
    }

    public static List<BrandData> convert(List<BrandPojo> brandPojos) {
        List<BrandData> brandDatas = new ArrayList<BrandData>();
        for (BrandPojo brandPojo : brandPojos) {
            brandDatas.add(convert(brandPojo));
        }
        return brandDatas;

    }

    public static BrandPojo normalize(BrandPojo brandPojo) {
        brandPojo.setBrand(CommonsHelper.normalize(brandPojo.getBrand()));
        brandPojo.setCategory(CommonsHelper.normalize(brandPojo.getCategory()));
        return brandPojo;
    }

    public static BrandForm normalize(BrandForm brandForm) {
        brandForm.setBrand(CommonsHelper.normalize(brandForm.getBrand()));
        brandForm.setCategory(CommonsHelper.normalize(brandForm.getCategory()));
        return brandForm;
    }

}
