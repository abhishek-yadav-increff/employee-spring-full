package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class InventoryApiController {

    @Autowired
    private InventoryService service;

    @ApiOperation(value = "Adds an inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
    public void add(@RequestBody InventoryForm form) throws ApiException {
        InventoryPojo p = convert(form);
        service.add(p);
    }


    @ApiOperation(value = "Deletes and inventory")
    @RequestMapping(path = "/api/inventory/{barcode}", method = RequestMethod.DELETE)
    // /api/1
    public void delete(@PathVariable String barcode) {
        service.delete(barcode);
    }

    @ApiOperation(value = "Gets an inventory by ID")
    @RequestMapping(path = "/api/inventory/{barcode}", method = RequestMethod.GET)
    public InventoryForm get(@PathVariable String barcode) throws ApiException {
        InventoryPojo p = service.get(barcode);
        return convert(p);
    }

    @ApiOperation(value = "Gets list of all inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
    public List<InventoryForm> getAll() {
        List<InventoryPojo> list = service.getAll();
        List<InventoryForm> list2 = new ArrayList<InventoryForm>();
        for (InventoryPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    @ApiOperation(value = "Updates an inventory")
    @RequestMapping(path = "/api/inventory/{barcode}", method = RequestMethod.PUT)
    public void update(@PathVariable String barcode, @RequestBody InventoryForm f)
            throws ApiException {
        InventoryPojo p = convert(f);
        service.update(barcode, p);
    }


    private static InventoryForm convert(InventoryPojo p) {
        InventoryForm d = new InventoryForm();
        d.setQuantity(p.getQuantity());
        d.setBarcode(p.getBarcode());
        return d;
    }

    private static InventoryPojo convert(InventoryForm f) {
        InventoryPojo p = new InventoryPojo();
        p.setQuantity(f.getQuantity());
        p.setBarcode(f.getBarcode());
        return p;
    }

}
