package com.increff.employee.dto.helper;

import java.util.ArrayList;
import java.util.List;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;

/**
 * BrandReportDtoHelper
 */
public class BrandReportDtoHelper {

    public static List<BrandForm> convert(List<BrandPojo> p) {
        List<BrandForm> list2 = new ArrayList<BrandForm>();
        for (BrandPojo pp : p) {
            list2.add(convert(pp));
        }
        return list2;
    }

    public static BrandForm convert(BrandPojo p) {
        BrandForm d = new BrandForm();
        d.setBrand(p.getBrand());
        d.setCategory(p.getCategory());
        return d;
    }
}
