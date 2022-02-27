package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.increff.employee.dao.InventoryDao;
import com.increff.employee.pojo.InventoryPojo;

@Service
public class InventoryService {

    @Autowired
    private InventoryDao dao;

    @Autowired
    private ProductService productService;


    @Transactional(rollbackOn = ApiException.class)
    public void add(InventoryPojo inventoryPojoNew) throws ApiException {
        productService.getByBarcode(inventoryPojoNew.getBarcode());
        if (inventoryPojoNew.getQuantity() <= 0) {
            throw new ApiException("Quantity must be positive!");
        }
        InventoryPojo inventoryPojoOld = dao.select(inventoryPojoNew.getBarcode());
        if (inventoryPojoOld != null) {
            inventoryPojoOld
                    .setQuantity(inventoryPojoNew.getQuantity() + inventoryPojoOld.getQuantity());
            update(inventoryPojoOld.getBarcode(), inventoryPojoOld);
        } else {
            dao.insert(inventoryPojoNew);
        }
    }

    public InventoryPojo get(String barcode) throws ApiException {
        return getCheck(barcode);
    }

    public List<InventoryPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(String barcode, InventoryPojo p) throws ApiException {
        productService.getByBarcode(p.getBarcode());
        if (p.getQuantity() < 0) {
            throw new ApiException("Quantity can not be negative");
        }
        InventoryPojo ex = get(barcode);
        ex.setQuantity(p.getQuantity());
        dao.update(ex);
    }

    public InventoryPojo getCheck(String barcode) throws ApiException {
        InventoryPojo p = dao.select(barcode);
        if (p == null) {
            throw new ApiException("Inventory with given ID does not exit, id: " + barcode);
        }
        return p;
    }

}
