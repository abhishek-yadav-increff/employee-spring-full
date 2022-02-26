package com.increff.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.increff.employee.dto.OrderItemDto;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.model.OrderItemXmlForm;
import com.increff.employee.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class OrderItemApiController {


    @Autowired
    private OrderItemDto orderItemDto;

    @ApiOperation(value = "Adds an orderItem")
    @RequestMapping(path = "/api/orderItem", method = RequestMethod.POST)
    public void add(@RequestBody OrderItemForm form) throws ApiException {
        orderItemDto.add(form);
    }

    @ApiOperation(value = "Deletes and orderItem")
    @RequestMapping(path = "/api/orderItem/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws ApiException {
        orderItemDto.delete(id);
    }

    @ApiOperation(value = "Gets an orderItem by ID")
    @RequestMapping(path = "/api/orderItem/{id}", method = RequestMethod.GET)
    public OrderItemForm get(@PathVariable int id) throws ApiException {
        return orderItemDto.get(id);
    }

    @ApiOperation(value = "Gets an orderItem by ID")
    @RequestMapping(path = "/api/orderItem/orderId/{id}", method = RequestMethod.GET)
    public List<OrderItemXmlForm> getByOrderId(@PathVariable int id) throws ApiException {
        return orderItemDto.getByOrderId(id);
    }

    @ApiOperation(value = "Gets list of all employees")
    @RequestMapping(path = "/api/orderItem", method = RequestMethod.GET)
    public List<OrderItemXmlForm> getAll() throws ApiException {
        return orderItemDto.getAll();
    }

    @ApiOperation(value = "Updates an orderItem")
    @RequestMapping(path = "/api/orderItem/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody OrderItemForm f) throws ApiException {
        orderItemDto.update(id, f);
    }

}
