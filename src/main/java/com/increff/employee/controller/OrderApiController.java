package com.increff.employee.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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

import com.increff.employee.model.OrderForm;
import com.increff.employee.model.OrderItemForms;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class OrderApiController {

    @Autowired
    private OrderService service;

    @Autowired
    private OrderItemService orderItemService;

    // @ApiOperation(value = "Adds an order")
    // @RequestMapping(path = "/api/order", method = RequestMethod.POST)
    // public void add() throws ApiException {
    // OrderPojo p = new OrderPojo();
    // service.add(p);
    // }
    @ApiOperation(value = "Adds an order")
    @RequestMapping(value = "/api/order", method = RequestMethod.POST)
    public Integer add() throws ApiException, IOException {
        // your stuff goes here
        OrderPojo p = new OrderPojo();
        service.add(p);
        return p.getId();
        // // important line
        // res.sendRedirect("http://localhost:9000/employee/ui/orderEdit/" + p.getId().toString());
        // // return "redirect:/ui/orderEdit/" + p.getId();
        // HttpHeaders headers = new HttpHeaders();
        // headers.setLocation(URI.create("employee/ui/orderEdit/" + p.getId().toString()));
        // return new ResponseEntity<>(headers, HttpStatus.RESET_CONTENT);
    }


    @ApiOperation(value = "Deletes and order")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.DELETE)
    // /api/1
    public void delete(@PathVariable int id) throws ApiException {
        service.delete(id);
    }

    @ApiOperation(value = "Deletes and order")
    @RequestMapping(path = "/api/order/senddOrder/{id}", method = RequestMethod.PUT)
    // /api/1
    public void sendOrder(@PathVariable int id) throws ApiException {
        OrderPojo p = service.get(id);
        p.setComplete(1);
        service.update(id, p);

    }

    @ApiOperation(value = "Gets an order by ID")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
    public OrderForm get(@PathVariable int id) throws ApiException {
        OrderPojo p = service.get(id);
        return convert(p);
    }

    @ApiOperation(value = "Gets list of all order")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<OrderForm> getAll() {
        List<OrderPojo> list = service.getAll();
        List<OrderForm> list2 = new ArrayList<OrderForm>();
        for (OrderPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    @ApiOperation(value = "Stores order as xml object")
    @RequestMapping(path = "/api/order/sendOrder/{id}", method = RequestMethod.PUT)
    public void storeOrder(@PathVariable Integer id)
            throws JAXBException, FileNotFoundException, ApiException {
        service.setComplete(id);
        String fname = service.generateXML(id);
        service.generatePdf(fname);
        // OrderPojo p = service.get(id);
        // p.setComplete(1);
        // service.update(id, p);

    }



    @ApiOperation(value = "Updates an order")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody OrderForm f) throws ApiException {
        OrderPojo p = convert(f);
        service.update(id, p);
    }


    private static OrderForm convert(OrderPojo p) {
        OrderForm d = new OrderForm();
        d.setTime(p.getTime());
        d.setId(p.getId());
        d.setComplete(p.getComplete());
        d.setCost(p.getCost());
        return d;
    }

    private static OrderPojo convert(OrderForm f) {
        OrderPojo p = new OrderPojo();
        // p.setTime(f.getTime());
        p.setId(f.getId());
        p.setComplete(f.getComplete());
        p.setCost(f.getCost());
        return p;
    }

}
