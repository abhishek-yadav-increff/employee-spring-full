package com.increff.employee.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.increff.employee.dto.BrandReportDto;
import com.increff.employee.model.BrandForm;
import com.increff.employee.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class BrandReportController {

    @Autowired
    private BrandReportDto brandReportDto;

    @ApiOperation(value = "Gets list of all brands")
    @RequestMapping(path = "/api/brandreport", method = RequestMethod.GET)
    public List<BrandForm> getAll() throws ApiException {
        return brandReportDto.getAll();
    }

}
