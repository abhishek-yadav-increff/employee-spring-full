package com.increff.employee.dto;

import java.util.List;
import com.increff.employee.dto.helper.BrandReportDtoHelper;
import com.increff.employee.model.BrandData;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BrandReportDto
 */
@Service
public class BrandReportDto {
    @Autowired
    private BrandReportService brandReportService;

    public List<BrandData> getAll() throws ApiException {
        List<BrandPojo> list = brandReportService.get();
        return BrandReportDtoHelper.convert(list);
    }

}
