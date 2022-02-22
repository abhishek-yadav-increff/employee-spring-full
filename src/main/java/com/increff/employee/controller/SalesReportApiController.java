package com.increff.employee.controller;


import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.increff.employee.dto.SalesReportDto;
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
    public List<SalesReportDto> getAll(@PathVariable String jsonData) throws ApiException {
        try {
            // System.out.print(jsonData);
            SalesReportForm salesReportForm =
                    objectMapper.readValue(jsonData, SalesReportForm.class);
            List<SalesReportDto> salesReportDtos = salesReportService.get(salesReportForm);
            System.out.println(salesReportDtos.size());
            System.out.println(salesReportDtos);
            return salesReportDtos;

        } catch (Exception e) {
            throw new ApiException("Can not parse data");
        }

    }

}
