package com.increff.employee.dto;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.increff.employee.dto.helper.SalesReportDtoHelper;
import com.increff.employee.model.SalesReportData;
import com.increff.employee.model.SalesReportForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.SalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SalesReportDto
 */
@Service
public class SalesReportDto {
    @Autowired
    private SalesReportService salesReportService;

    @Autowired
    private BrandService brandService;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public List<SalesReportData> getAll(String jsonData) throws ApiException {
        SalesReportForm salesReportForm = new SalesReportForm();
        try {
            salesReportForm = objectMapper.readValue(jsonData, SalesReportForm.class);
        } catch (IOException ex) {
            throw new ApiException("Couldn't parse data!");
        }
        salesReportForm = SalesReportDtoHelper.normalize(salesReportForm);
        Date startDate = SalesReportDtoHelper.getStartDate(salesReportForm);
        Date endDate = SalesReportDtoHelper.getEndDate(salesReportForm);
        List<BrandPojo> brandPojos = brandService.getListByBrandAndCategory(salesReportForm.brand,
                salesReportForm.category);
        Set<Integer> brandCategoryIds = SalesReportDtoHelper.getBrandCategoryIds(brandPojos);
        List<SalesReportData> salesReportDatas =
                salesReportService.get(salesReportForm, startDate, endDate, brandCategoryIds);

        salesReportDatas = SalesReportDtoHelper.normalizeDouble(salesReportDatas);
        return salesReportDatas;
    }

}
