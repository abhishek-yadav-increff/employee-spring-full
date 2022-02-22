package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class BrandApiController {

    @Autowired
    private BrandService service;

    @ApiOperation(value = "Adds an Brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm form) throws ApiException {
        BrandPojo p = convert(form);
        service.add(p);
    }


    @ApiOperation(value = "Deletes and Brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.DELETE)
    // /api/1
    public void delete(@PathVariable int id) throws ApiException {
        service.delete(id);
    }

    @ApiOperation(value = "Gets an Brand by ID")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.GET)
    public BrandData get(@PathVariable int id) throws ApiException {
        BrandPojo p = service.get(id);
        return convert(p);
    }

    @ApiOperation(value = "Gets an Brand by category")
    @RequestMapping(path = "/api/brand/byCategory/{category}", method = RequestMethod.GET)
    public List<BrandData> getByCategory(@PathVariable String category) throws ApiException {
        List<BrandPojo> p = service.getByCategory(category);
        if (p.isEmpty()) {
            throw new ApiException("Category doesn't exist");
        }
        return convert(p);
    }



    @ApiOperation(value = "Gets an Brand by brand")
    @RequestMapping(path = "/api/brand/byBrand/{brand}", method = RequestMethod.GET)
    public List<BrandData> getByBrand(@PathVariable String brand) throws ApiException {
        List<BrandPojo> p = service.getByBrand(brand);
        if (p.isEmpty()) {
            throw new ApiException("Brand doesn't exist");
        }
        return convert(p);
    }

    @ApiOperation(value = "Gets a Brand by brand and category")
    @RequestMapping(path = "/api/brand/search/{brand}/{category}", method = RequestMethod.GET)
    public List<BrandData> getListByBrandAndCategory(@PathVariable String brand,
            @PathVariable String category) throws ApiException {

        List<BrandPojo> p = service.getListByBrandAndCategory(brand, category);
        // if ((brand == null) || brand.isEmpty()) {
        // p = service.getByCategory(brand);
        // } else if ((category == null) || category.isEmpty()) {
        // p = service.getByBrand(brand);
        // } else {
        // p = Arrays.asList(service.getByBrandAndCategory(brand, category));
        // // p = ;
        // }
        return convert(p);
    }

    @ApiOperation(value = "Gets list of all Brands")
    @RequestMapping(path = "/api/brand", method = RequestMethod.GET)
    public List<BrandData> getAll() {
        List<BrandPojo> list = service.getAll();

        return convert(list);
    }

    @ApiOperation(value = "Updates an Brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandForm f) throws ApiException {
        BrandPojo p = convert(f);
        service.update(id, p);
    }


    private static BrandData convert(BrandPojo p) {
        BrandData d = new BrandData();
        d.setBrand(p.getBrand());
        d.setCategory(p.getCategory());
        d.setId(p.getId());
        return d;
    }

    private static BrandPojo convert(BrandForm f) {
        BrandPojo p = new BrandPojo();
        p.setBrand(f.getBrand());
        p.setCategory(f.getCategory());
        return p;
    }

    private List<BrandData> convert(List<BrandPojo> p) {
        List<BrandData> list2 = new ArrayList<BrandData>();
        for (BrandPojo pp : p) {
            list2.add(convert(pp));
        }
        return list2;
    }

}
