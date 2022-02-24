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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.increff.employee.model.SalesReportData;
import com.increff.employee.model.SalesReportForm;
import com.increff.employee.service.ApiException;
// import com.increff.employee.service.SalesReportService;
import com.increff.employee.service.SalesReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class SalesReportApiController {

    @Autowired
    private SalesReportService salesReportService;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @ApiOperation(value = "Gets list of all order")
    @RequestMapping(path = "/api/reportsSales/{jsonData}", method = RequestMethod.GET)
    public List<SalesReportData> getAll(@PathVariable String jsonData)
            throws ApiException, JsonParseException, JsonMappingException, IOException {

        // System.out.print(jsonData);
        SalesReportForm salesReportForm = new SalesReportForm();
        try {
            salesReportForm = objectMapper.readValue(jsonData, SalesReportForm.class);
        } catch (IOException ex) {
            throw new ApiException("Couldn't parse data!");
        }
        System.out.print(salesReportForm);
        List<SalesReportData> salesReportDtos = salesReportService.get(salesReportForm);
        System.out.println(salesReportDtos.size());
        System.out.println(salesReportDtos);
        return salesReportDtos;

    }

}
