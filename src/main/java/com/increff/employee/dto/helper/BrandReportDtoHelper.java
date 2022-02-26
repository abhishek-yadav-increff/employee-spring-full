package com.increff.employee.dto.helper;

import java.util.ArrayList;
import java.util.List;
import com.increff.employee.model.BrandData;
import com.increff.employee.pojo.BrandPojo;

/**
 * BrandReportDtoHelper
 */
public class BrandReportDtoHelper {

    public static List<BrandData> convert(List<BrandPojo> p) {
        List<BrandData> list2 = new ArrayList<BrandData>();
        for (BrandPojo pp : p) {
            list2.add(convert(pp));
        }
        return list2;
    }

    public static BrandData convert(BrandPojo p) {
        BrandData d = new BrandData();
        d.setBrand(p.getBrand());
        d.setCategory(p.getCategory());
        d.setId(p.getId());
        return d;
    }
}
