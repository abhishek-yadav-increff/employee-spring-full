package com.increff.employee.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.increff.employee.dto.InventoryReportDto;
import com.increff.employee.model.InventoryReportData;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class InventoryReportApiController {

    @Autowired
    private InventoryReportDto inventoryReportDto;

    @ApiOperation(value = "Gets report of items in inventory")
    @RequestMapping(path = "/api/inventoryreport", method = RequestMethod.GET)
    public List<InventoryReportData> getAll() throws ApiException {
        return inventoryReportDto.getAll();
    }

}
