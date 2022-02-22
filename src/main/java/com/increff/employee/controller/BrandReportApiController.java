package com.increff.employee.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.increff.employee.model.BrandData;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandReportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class BrandReportApiController {

    @Autowired
    private BrandReportService brandReportService;


    @ApiOperation(value = "Gets list of all order")
    @RequestMapping(path = "/api/brandreport", method = RequestMethod.GET)
    public List<BrandData> getAll() throws ApiException {
        List<BrandPojo> list = brandReportService.get();
        return convert(list);
    }

    private static List<BrandData> convert(List<BrandPojo> p) {
        List<BrandData> list2 = new ArrayList<BrandData>();
        for (BrandPojo pp : p) {
            list2.add(convert(pp));
        }
        return list2;
    }

    private static BrandData convert(BrandPojo p) {
        BrandData d = new BrandData();
        d.setBrand(p.getBrand());
        d.setCategory(p.getCategory());
        d.setId(p.getId());
        return d;
    }
}
