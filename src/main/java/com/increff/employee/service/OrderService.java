package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;

@Service
public class OrderService {

    @Autowired
    private OrderDao dao;

    @Autowired
    private OrderItemService orderItemService;

    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderPojo p) throws ApiException {
        dao.insert(p);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void delete(Integer id) throws ApiException {
        OrderPojo orderPojo = get(id);
        if (orderPojo.getComplete() == 1) {
            throw new ApiException("Cannot delete completed order");
        }
        List<OrderItemPojo> orderItemPojos = orderItemService.getByOrderId(id);
        for (OrderItemPojo orderItemPojo : orderItemPojos) {
            orderItemService.delete(orderItemPojo.getId());
        }
        dao.delete(id);
    }

    public OrderPojo get(Integer id) throws ApiException {
        return getCheck(id);
    }

    public List<OrderPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(Integer id, OrderPojo p) throws ApiException {
        OrderPojo ex = getCheck(id);
        ex.setComplete(p.getComplete());
        ex.setCost(p.getCost());
        dao.update(ex);
    }

    public OrderPojo getCheck(Integer id) throws ApiException {
        OrderPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Order with given ID does not exit, id: " + id);
        }
        return p;
    }

    public List<OrderPojo> getByTime(long time, long time2) throws ApiException {
        List<OrderPojo> p = dao.selectByTime(time, time2);
        if (p == null) {
            throw new ApiException("No orders within constrained dates");
        }
        return p;
    }

    @Transactional(rollbackOn = ApiException.class)
    public void setComplete(int id) throws ApiException {
        OrderPojo p = get(id);
        if (orderItemService.getByOrderId(p.getId()).size() == 0) {
            throw new ApiException("There are no order items!");
        }
        p.setComplete(1);
        update(id, p);
    }

}
