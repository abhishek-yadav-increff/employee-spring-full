package com.increff.employee.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.OrderForm;
import com.increff.employee.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class OrderApiController {

    @Autowired
    private OrderDto orderDto;


    @ApiOperation(value = "Adds an order")
    @RequestMapping(value = "/api/order", method = RequestMethod.POST)
    public Integer add() throws ApiException, IOException {
        return orderDto.get();
    }

    @ApiOperation(value = "Deletes an order")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws ApiException {
        orderDto.delete(id);
    }

    @ApiOperation(value = "Gets an order by ID")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
    public OrderForm get(@PathVariable int id) throws ApiException {
        return orderDto.get(id);
    }

    @ApiOperation(value = "Gets list of all order")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<OrderForm> getAll() {
        return orderDto.getAll();
    }

    @ApiOperation(value = "Checkout order and generate pdf")
    @RequestMapping(path = "/api/order/sendOrder/{id}", method = RequestMethod.PUT)
    public void storeOrder(@PathVariable Integer id)
            throws JAXBException, FileNotFoundException, ApiException {
        orderDto.storeOrder(id);
    }

    @ApiOperation(value = "Gets pdf of order Invoice")
    @RequestMapping(path = "/api/order/getPdf/{id}", method = RequestMethod.GET)
    public @ResponseBody byte[] getPdf(@PathVariable Integer id) throws ApiException, IOException {
        return orderDto.getPdf(id);
    }

    @ApiOperation(value = "Updates an order")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody OrderForm f) throws ApiException {
        orderDto.update(id, f);
    }

}
