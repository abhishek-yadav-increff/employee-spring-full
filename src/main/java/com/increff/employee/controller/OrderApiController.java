package com.increff.employee.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.OrderForm;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class OrderApiController {

    @Autowired
    private OrderService service;



    @ApiOperation(value = "Adds an order")
    @RequestMapping(value = "/api/order", method = RequestMethod.POST)
    public Integer add() throws ApiException, IOException {
        // your stuff goes here
        OrderPojo p = new OrderPojo();
        service.add(p);
        return p.getId();
    }


    @ApiOperation(value = "Deletes and order")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.DELETE)
    // /api/1
    public void delete(@PathVariable int id) throws ApiException {
        service.delete(id);
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

    @ApiOperation(value = "Gets pdf order Invoice")
    @RequestMapping(path = "/api/order/getPdf/{id}", method = RequestMethod.GET)
    public @ResponseBody byte[] getPdf(@PathVariable Integer id) throws ApiException, IOException {
        String filepath =
                "/home/abhk943/Documents/increff/employee-spring-full2/xml_data/generated_pdf/order_"
                        + id.toString() + ".pdf";
        try {
            byte[] inFileBytes = Files.readAllBytes(Paths.get(filepath));
            byte[] encoded = org.apache.commons.codec.binary.Base64.encodeBase64(inFileBytes);
            return encoded;
        } catch (IOException e) {
            System.out.print(e.toString());
        }
        return null;
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
