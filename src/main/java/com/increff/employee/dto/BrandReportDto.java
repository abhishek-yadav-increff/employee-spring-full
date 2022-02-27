package com.increff.employee.dto;

import java.util.List;
import com.increff.employee.dto.helper.BrandReportDtoHelper;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
// import com.increff.employee.service.BrandReportService;
import com.increff.employee.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BrandReportDto
 */
@Service
public class BrandReportDto {
    // @Autowired
    // private BrandReportService brandReportService;

    @Autowired
    private BrandService brandService;

    public List<BrandForm> getAll() throws ApiException {
        List<BrandPojo> list = brandService.getAll();
        return BrandReportDtoHelper.convert(list);
    }

}
