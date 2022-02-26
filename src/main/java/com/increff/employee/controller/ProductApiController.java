package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class ProductApiController {

    @Autowired
    private ProductService service;


    @ApiOperation(value = "Adds an product")
    @RequestMapping(path = "/api/product", method = RequestMethod.POST)
    public void add(@RequestBody ProductForm form) throws ApiException {
        // ProductPojo p = convert(form);
        service.add(form);
    }


    @ApiOperation(value = "Deletes and product")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws ApiException {
        service.delete(id);
    }

    @ApiOperation(value = "Gets an product by ID")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.GET)
    public ProductData get(@PathVariable int id) throws ApiException {
        ProductPojo p = service.get(id);
        return service.convert(p);
    }

    @ApiOperation(value = "Gets an product by Product Barcode")
    @RequestMapping(path = "/api/product/byBarcode/{barcode}", method = RequestMethod.GET)
    public ProductData getByProductBarcode(@PathVariable String barcode) throws ApiException {
        ProductPojo p = service.getByBarcode(barcode);
        return service.convert(p);
    }


    @ApiOperation(value = "Gets list of all employees")
    @RequestMapping(path = "/api/product", method = RequestMethod.GET)
    public List<ProductData> getAll() throws ApiException {
        System.out.print("here");
        List<ProductPojo> list = service.getAll();
        System.out.print("after getAll");
        for (ProductPojo p : list) {
            System.out.print(p.getBarcode());
        }
        List<ProductData> list2 = new ArrayList<ProductData>();
        for (ProductPojo p : list) {
            System.out.print("before convert");

            list2.add(service.convert(p));
            System.out.print(p.getId());
        }
        return list2;
    }

    @ApiOperation(value = "Updates an product")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody ProductForm f) throws ApiException {
        service.update(id, f);
    }


    // private static ProductData convert(ProductPojo p) {
    // ProductData d = new ProductData();
    // BrandPojo brandPojo = brandService.getByBrandAndCategory(d.getBrand(), d.getCategory());
    // d.setId(p.getId());
    // d.setName(p.getName());
    // d.setMrp(p.getMrp());
    // return d;
    // }

    // private static ProductPojo convert(ProductForm f) {
    // ProductPojo p = new ProductPojo();
    // p.setBarcode(f.getBarcode());
    // p.setName(f.getName());
    // p.setMrp(f.getMrp());
    // return p;
    // }

}
