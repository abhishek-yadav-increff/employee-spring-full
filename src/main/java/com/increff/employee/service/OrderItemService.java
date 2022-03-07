package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.dto.helper.CommonsHelper;
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

        checkValidation(p);

        ProductPojo productPojo = productService.getByBarcode(p.getProductBarcode());
        p.setSellingPrice(CommonsHelper.normalize(p.getQuantity() * productPojo.getMrp()));

        InventoryPojo inventoryPojo = inventoryService.get(p.getProductBarcode());
        if (inventoryPojo.getQuantity() < p.getQuantity()) {
            throw new ApiException("Your order item exceeds inventory quantity(max: "
                    + inventoryPojo.getQuantity() + ")!");
        }

        orderService.get(p.getOrderId());
        productService.getByBarcode(p.getProductBarcode());

        OrderItemPojo pp = dao.getByProductBarcodeAndOrderId(p.getProductBarcode(), p.getOrderId());
        if (pp != null) {
            updateOnAddition(pp, p);
        } else {
            inventoryPojo.setQuantity(inventoryPojo.getQuantity() - p.getQuantity());
            inventoryService.update(inventoryPojo.getBarcode(), inventoryPojo);

            OrderPojo orderPojo = orderService.get(p.getOrderId());
            orderPojo.setCost(orderPojo.getCost() + p.getSellingPrice());
            orderService.update(orderPojo.getId(), orderPojo);

            dao.insert(p);
        }
    }

    @Transactional(rollbackOn = ApiException.class)
    private void updateOnAddition(OrderItemPojo pp, OrderItemPojo p) throws ApiException {
        orderService.getCheck(p.getOrderId());

        InventoryPojo inventoryPojo = inventoryService.get(p.getProductBarcode());
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
        dao.update(pp);
    }

    private void checkValidation(OrderItemPojo p) throws ApiException {
        if (orderService.getCheck(p.getOrderId()).getComplete() == 1) {
            throw new ApiException("Can not do this operation on completed order!");
        }
        if (p.getProductBarcode() == null || p.getProductBarcode().isEmpty()) {
            throw new ApiException("Barcode can not be empty!");
        }
        if (p.getQuantity() == null) {
            throw new ApiException("Quantity can not be empty!");
        }
        if (p.getQuantity() <= 0) {
            throw new ApiException("Quantity must be positive!");
        }
    }

    @Transactional(rollbackOn = ApiException.class)
    public void delete(int id) throws ApiException {

        OrderItemPojo p = dao.select(id);
        if (p != null && orderService.getCheck(p.getOrderId()).getComplete() == 1) {
            throw new ApiException("Can not do this operation on completed order!");
        }
        InventoryPojo inventoryPojo = inventoryService.get(p.getProductBarcode());
        OrderPojo orderPojo = orderService.get(p.getOrderId());

        inventoryPojo.setQuantity(inventoryPojo.getQuantity() + p.getQuantity());
        inventoryService.update(inventoryPojo.getBarcode(), inventoryPojo);

        orderPojo.setCost(orderPojo.getCost() - p.getSellingPrice());
        orderService.update(orderPojo.getId(), orderPojo);

        dao.delete(id);
    }

    public List<OrderItemPojo> getByOrderId(int orderId) {
        return dao.getByOrderId(orderId);
    }

    public OrderItemPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    public List<OrderItemPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, OrderItemPojo p) throws ApiException {
        if (orderService.getCheck(p.getOrderId()).getComplete() == 1) {
            throw new ApiException("Can not do this operation on completed order!");
        }
        ProductPojo productPojo = productService.getByBarcode(p.getProductBarcode());
        p.setSellingPrice(p.getQuantity() * productPojo.getMrp());

        OrderPojo orderPojo = orderService.get(p.getOrderId());
        productService.getByBarcode(p.getProductBarcode());

        if (p.getQuantity() <= 0) {
            throw new ApiException("Quantity must be positive!");
        }

        OrderItemPojo ex = getCheck(id);
        InventoryPojo inventoryPojo = inventoryService.get(ex.getProductBarcode());

        Integer maxQuantity = inventoryPojo.getQuantity() + ex.getQuantity();
        if (p.getQuantity() > maxQuantity) {
            throw new ApiException("Exceeds Inventory Quantity( max: " + maxQuantity + ")");
        }
        inventoryPojo.setQuantity(maxQuantity - p.getQuantity());
        inventoryService.update(inventoryPojo.getBarcode(), inventoryPojo);

        orderPojo.setCost(orderPojo.getCost() + p.getSellingPrice() - ex.getSellingPrice());
        orderService.update(orderPojo.getId(), orderPojo);

        ex.setQuantity(p.getQuantity());
        ex.setSellingPrice(p.getSellingPrice());
        dao.update(ex);

    }

    public OrderItemPojo getCheck(int id) throws ApiException {
        OrderItemPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("OrderItem with given ID does not exit, id: " + id);
        }
        return p;
    }

}
