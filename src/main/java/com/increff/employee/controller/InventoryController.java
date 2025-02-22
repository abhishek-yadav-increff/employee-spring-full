package com.increff.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.increff.employee.dto.InventoryDto;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class InventoryController {

    @Autowired
    private InventoryDto inventoryDto;

    @ApiOperation(value = "Adds an inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
    public void add(@RequestBody InventoryForm inventoryForm) throws ApiException {
        inventoryDto.add(inventoryForm);
    }

    @ApiOperation(value = "Gets an inventory by barcode")
    @RequestMapping(path = "/api/inventory/{barcode}", method = RequestMethod.GET)
    public InventoryForm get(@PathVariable String barcode) throws ApiException {
        return inventoryDto.get(barcode);
    }

    @ApiOperation(value = "Gets list of all inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
    public List<InventoryForm> getAll() throws ApiException {
        return inventoryDto.getAll();
    }

    @ApiOperation(value = "Updates an inventory")
    @RequestMapping(path = "/api/inventory/{barcode}", method = RequestMethod.PUT)
    public void update(@PathVariable String barcode, @RequestBody InventoryForm inventoryForm)
            throws ApiException {
        inventoryDto.update(barcode, inventoryForm);
    }


}
