package com.increff.employee.dto.helper;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.increff.employee.model.SalesReportData;
import com.increff.employee.model.SalesReportForm;
import com.increff.employee.pojo.BrandPojo;

/**
 * SalesReportDtoHelper
 */
public class SalesReportDtoHelper {

    public static SalesReportForm normalize(SalesReportForm data) {
        if (data.startDate.equals("NaN")) {
            data.startDate = "0000000000000";
        }
        if (data.endDate.equals("NaN")) {
            data.endDate = "9999999999999";
        }
        return data;
    }

    public static Date getStartDate(SalesReportForm salesReportForm) {
        Date startDate = new Date(Long.parseLong(salesReportForm.startDate));
        return startDate;
    }

    public static Date getEndDate(SalesReportForm salesReportForm) {
        Date endDate =
                new Date(Long.parseLong(salesReportForm.endDate) + (1000 * 60 * 60 * 24) - 1);
        return endDate;
    }

    public static Set<Integer> getBrandCategoryIds(List<BrandPojo> brandPojos) {
        Set<Integer> brandCategoryIds = new HashSet<Integer>();
        for (BrandPojo brandPojo : brandPojos) {
            brandCategoryIds.add(brandPojo.getId());
        }
        return brandCategoryIds;
    }

    public static List<SalesReportData> normalizeDouble(List<SalesReportData> salesReportDatas) {
        for (SalesReportData salesReportData : salesReportDatas) {
            salesReportData.setRevenue(CommonsHelper.normalize(salesReportData.getRevenue()));
        }
        return salesReportDatas;
    }

}
