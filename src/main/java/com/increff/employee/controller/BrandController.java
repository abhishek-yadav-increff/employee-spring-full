package com.increff.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.increff.employee.dto.BrandDto;
import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class BrandController {

    @Autowired
    private BrandDto brandDto;

    @ApiOperation(value = "Adds a Brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm form) throws ApiException {
        brandDto.add(form);
    }

    @ApiOperation(value = "Gets a Brand by ID")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.GET)
    public BrandData get(@PathVariable int id) throws ApiException {
        return brandDto.get(id);
    }

    @ApiOperation(value = "Gets list of all Brands")
    @RequestMapping(path = "/api/brand", method = RequestMethod.GET)
    public List<BrandData> getAll() throws ApiException {
        return brandDto.getAll();
    }

    @ApiOperation(value = "Gets list of Brands by category")
    @RequestMapping(path = "/api/brand/category/{category}", method = RequestMethod.GET)
    public List<BrandData> getByCategory(@PathVariable String category) throws ApiException {
        return brandDto.getByCategory(category);
    }

    @ApiOperation(value = "Gets list of Brands by brand")
    @RequestMapping(path = "/api/brand/brand/{brand}", method = RequestMethod.GET)
    public List<BrandData> getByBrand(@PathVariable String brand) throws ApiException {
        return brandDto.getByBrand(brand);
    }

    @ApiOperation(value = "Gets list of Brand by brand and category with no constraints on input")
    @RequestMapping(path = "/api/brand/search/{brand}/{category}", method = RequestMethod.GET)
    public List<BrandData> getListByBrandAndCategory(@PathVariable String brand,
            @PathVariable String category) throws ApiException {
        return brandDto.getListByBrandAndCategory(brand, category);
    }



    @ApiOperation(value = "Updates a Brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandForm f) throws ApiException {
        brandDto.update(id, f);
    }

}
