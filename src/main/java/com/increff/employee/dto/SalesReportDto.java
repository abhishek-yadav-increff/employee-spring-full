package com.increff.employee.dto;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.increff.employee.model.SalesReportData;
import com.increff.employee.model.SalesReportForm;
import com.increff.employee.service.ApiException;
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
    private static ObjectMapper objectMapper = new ObjectMapper();

    public List<SalesReportData> getAll(String jsonData) throws ApiException {
        SalesReportForm salesReportForm = new SalesReportForm();
        try {
            salesReportForm = objectMapper.readValue(jsonData, SalesReportForm.class);
        } catch (IOException ex) {
            throw new ApiException("Couldn't parse data!");
        }
        return salesReportService.get(salesReportForm);
    }

}
