package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;
import com.increff.employee.dto.helper.OrderItemDtoHelper;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.model.OrderItemXmlForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * OrderItemDto
 */
@Service
public class OrderItemDto {
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;

    public void add(OrderItemForm form) throws ApiException {
        OrderItemPojo p = OrderItemDtoHelper.convert(form);
        p = OrderItemDtoHelper.normalize(p);
        orderItemService.add(p);
    }

    public void delete(int id) throws ApiException {
        orderItemService.delete(id);
    }

    public OrderItemForm get(int id) throws ApiException {
        OrderItemPojo p = orderItemService.get(id);
        ProductPojo productPojo = productService.getByBarcode(p.getProductBarcode());
        return OrderItemDtoHelper.convertForm(p, productPojo.getMrp(), productPojo.getName());
    }

    public List<OrderItemXmlForm> getByOrderId(int id) throws ApiException {
        List<OrderItemPojo> orderItemPojos = orderItemService.getByOrderId(id);
        List<OrderItemXmlForm> list2 = new ArrayList<OrderItemXmlForm>();
        for (OrderItemPojo p : orderItemPojos) {
            ProductPojo productPojo = productService.getByBarcode(p.getProductBarcode());
            list2.add(OrderItemDtoHelper.convert(p, productPojo.getMrp(), productPojo.getName()));
        }
        return list2;
    }

    public List<OrderItemXmlForm> getAll() throws ApiException {
        List<OrderItemPojo> list = orderItemService.getAll();
        List<OrderItemXmlForm> list2 = new ArrayList<OrderItemXmlForm>();
        for (OrderItemPojo p : list) {
            ProductPojo productPojo = productService.getByBarcode(p.getProductBarcode());
            list2.add(OrderItemDtoHelper.convert(p, productPojo.getMrp(), productPojo.getName()));
        }
        return list2;
    }

    public void update(int id, OrderItemForm f) throws ApiException {
        OrderItemPojo p = OrderItemDtoHelper.convert(f);
        orderItemService.update(id, p);
    }


}
