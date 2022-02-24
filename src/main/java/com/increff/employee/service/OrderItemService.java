package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao dao;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private InventoryService inventoryService;

    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderItemPojo p) throws ApiException {
        if (p.getProductBarcode() == null || p.getProductBarcode().isEmpty()) {
            throw new ApiException("Barcode can not be empty!");
        }

        if (p.getQuantity() == null) {
            throw new ApiException("Quantity can not be empty!");
        }

        ProductPojo productPojo = productService.getByBarcode(p.getProductBarcode());
        p.setSellingPrice(p.getQuantity() * productPojo.getMrp());

        if (p.getQuantity() < 0) {
            throw new ApiException("Quantity can not be negative");
        }

        InventoryPojo inventoryPojo = inventoryService.get(p.getProductBarcode());
        if (inventoryPojo.getQuantity() < p.getQuantity()) {
            throw new ApiException("Your order exceeds inventory quantity(max: "
                    + inventoryPojo.getQuantity() + ")!");
        }

        orderService.get(p.getOrderId());
        productService.getByBarcode(p.getProductBarcode());

        OrderItemPojo pp = dao.getByProductBarcodeAndOrderId(p.getProductBarcode(), p.getOrderId());
        if (pp != null) {

            OrderPojo orderPojo = orderService.get(p.getOrderId());

            Integer maxQuantity = inventoryPojo.getQuantity();
            if (p.getQuantity() > maxQuantity) {
                throw new ApiException("Exceeds Inventory Quantity( max: " + maxQuantity + ")");
            }

            inventoryPojo.setQuantity(maxQuantity - p.getQuantity());
            inventoryService.update(inventoryPojo.getBarcode(), inventoryPojo);

            orderPojo.setCost(orderPojo.getCost() + p.getSellingPrice());
            orderService.update(orderPojo.getId(), orderPojo);

            pp.setQuantity(pp.getQuantity() + p.getQuantity());
            pp.setSellingPrice(pp.getSellingPrice() + p.getSellingPrice());
            update(pp.getId(), pp);

        } else {
            dao.insert(p);
            inventoryPojo.setQuantity(inventoryPojo.getQuantity() - p.getQuantity());
            inventoryService.update(inventoryPojo.getBarcode(), inventoryPojo);

            OrderPojo orderPojo = orderService.get(p.getOrderId());
            orderPojo.setCost(orderPojo.getCost() + p.getSellingPrice());
            orderService.update(orderPojo.getId(), orderPojo);
        }


        // if(inventoryDao.select(p.getProductId()).getQuantity()>=p.getQuantity()){

        // }
    }

    @Transactional
    public void delete(int id) throws ApiException {

        OrderItemPojo p = dao.select(id);
        InventoryPojo inventoryPojo = inventoryService.get(p.getProductBarcode());
        OrderPojo orderPojo = orderService.get(p.getOrderId());

        inventoryPojo.setQuantity(inventoryPojo.getQuantity() + p.getQuantity());
        inventoryService.update(inventoryPojo.getBarcode(), inventoryPojo);

        orderPojo.setCost(orderPojo.getCost() - p.getSellingPrice());
        orderService.update(orderPojo.getId(), orderPojo);

        dao.delete(id);
    }

    @Transactional
    public void deleteByProductBarcode(String productBarcode) {
        dao.deleteByProductBarcode(productBarcode);
    }

    @Transactional
    public List<OrderItemPojo> getByOrderId(int orderId) {
        return dao.getByOrderId(orderId);
    }

    @Transactional(rollbackOn = ApiException.class)
    public OrderItemPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<OrderItemPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, OrderItemPojo p) throws ApiException {
        ProductPojo productPojo = productService.getByBarcode(p.getProductBarcode());
        p.setSellingPrice(p.getQuantity() * productPojo.getMrp());

        orderService.get(p.getOrderId());
        productService.getByBarcode(p.getProductBarcode());

        if (p.getQuantity() < 0) {
            throw new ApiException("Quantity can not be negative");
        }

        OrderItemPojo ex = getCheck(id);
        InventoryPojo inventoryPojo = inventoryService.get(ex.getProductBarcode());
        OrderPojo orderPojo = orderService.get(p.getOrderId());

        Integer maxQuantity = inventoryPojo.getQuantity() + ex.getQuantity();
        if (p.getQuantity() > maxQuantity) {
            throw new ApiException("Exceeds Inventory Quantity( max: " + maxQuantity + ")");
        }
        inventoryPojo.setQuantity(maxQuantity - p.getQuantity());
        inventoryService.update(inventoryPojo.getBarcode(), inventoryPojo);

        orderPojo.setCost(orderPojo.getCost() + p.getSellingPrice() - ex.getSellingPrice());
        orderService.update(orderPojo.getId(), orderPojo);

        // ex.setProductBarcode(p.getProductBarcode());
        ex.setQuantity(p.getQuantity());
        ex.setSellingPrice(p.getSellingPrice());
        dao.update(ex);

    }

    @Transactional
    public OrderItemPojo getCheck(int id) throws ApiException {
        OrderItemPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("OrderItem with given ID does not exit, id: " + id);
        }
        return p;
    }



}
