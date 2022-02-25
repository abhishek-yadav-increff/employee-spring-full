package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class OrderItemApiController {

    @Autowired
    private OrderItemService service;

    @ApiOperation(value = "Adds an orderItem")
    @RequestMapping(path = "/api/orderItem", method = RequestMethod.POST)
    public void add(@RequestBody OrderItemForm form) throws ApiException {
        OrderItemPojo p = convert(form);
        service.add(p);
    }


    @ApiOperation(value = "Deletes and orderItem")
    @RequestMapping(path = "/api/orderItem/{id}", method = RequestMethod.DELETE)
    // /api/1
    public void delete(@PathVariable int id) throws ApiException {
        service.delete(id);
    }

    @ApiOperation(value = "Gets an orderItem by ID")
    @RequestMapping(path = "/api/orderItem/{id}", method = RequestMethod.GET)
    public OrderItemForm get(@PathVariable int id) throws ApiException {
        OrderItemPojo p = service.get(id);
        return service.convert(p);
    }

    @ApiOperation(value = "Gets an orderItem by ID")
    @RequestMapping(path = "/api/orderItem/orderId/{id}", method = RequestMethod.GET)
    public List<OrderItemForm> getByOrderId(@PathVariable int id) throws ApiException {
        List<OrderItemPojo> orderItemPojos = service.getByOrderId(id);
        List<OrderItemForm> list2 = new ArrayList<OrderItemForm>();
        for (OrderItemPojo p : orderItemPojos) {
            list2.add(service.convert(p));
        }
        return list2;
    }

    @ApiOperation(value = "Gets list of all employees")
    @RequestMapping(path = "/api/orderItem", method = RequestMethod.GET)
    public List<OrderItemForm> getAll() throws ApiException {
        List<OrderItemPojo> list = service.getAll();
        List<OrderItemForm> list2 = new ArrayList<OrderItemForm>();
        for (OrderItemPojo p : list) {
            list2.add(service.convert(p));
        }
        return list2;
    }

    @ApiOperation(value = "Updates an orderItem")
    @RequestMapping(path = "/api/orderItem/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody OrderItemForm f) throws ApiException {
        OrderItemPojo p = convert(f);
        service.update(id, p);
    }



    private static OrderItemPojo convert(OrderItemForm f) {
        OrderItemPojo p = new OrderItemPojo();
        p.setOrderId(f.getOrderId());
        p.setQuantity(f.getQuantity());
        p.setProductBarcode(f.getProductBarcode());
        // p.setSellingPrice(f.getSellingPrice());
        // p.setId(f.getId());
        return p;
    }


}
