package com.increff.employee.controller;


import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.increff.employee.dto.SalesReportDto;
import com.increff.employee.model.SalesReportData;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class SalesReportApiController {

    @Autowired
    private SalesReportDto salesReportDto;

    @ApiOperation(value = "Gets list of all order")
    @RequestMapping(path = "/api/reportsSales/{jsonData}", method = RequestMethod.GET)
    public List<SalesReportData> getAll(@PathVariable String jsonData)
            throws ApiException, JsonParseException, JsonMappingException, IOException {
        return salesReportDto.getAll(jsonData);
    }

}
