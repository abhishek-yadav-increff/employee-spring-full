package com.increff.employee.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.increff.employee.model.InventoryReportData;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryReportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class InventoryReportApiController {

    @Autowired
    private InventoryReportService brandReportService;


    @ApiOperation(value = "Gets list of all order")
    @RequestMapping(path = "/api/inventoryreport", method = RequestMethod.GET)
    public List<InventoryReportData> getAll() throws ApiException {
        List<InventoryReportData> list = brandReportService.get();
        return list;
    }

}
