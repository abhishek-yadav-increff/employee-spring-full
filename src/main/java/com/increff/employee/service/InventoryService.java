package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.increff.employee.dao.InventoryDao;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;

@Service
public class InventoryService {

    @Autowired
    private InventoryDao dao;

    @Autowired
    private ProductService productService;


    @Transactional(rollbackOn = ApiException.class)
    public void add(InventoryPojo p) throws ApiException {
        if (productService.getByBarcode(p.getBarcode()) == null) {
            throw new ApiException("Product doesn't exist");
        }
        if (p.getQuantity() < 0) {
            throw new ApiException("Quantity can not be negative");
        }
        InventoryPojo pp = dao.select(p.getBarcode());
        if (pp != null) {
            pp.setQuantity(p.getQuantity() + pp.getQuantity());
            update(pp.getBarcode(), pp);
        } else {
            dao.insert(p);
        }
        System.out.println("No errors till dao insert");
    }

    @Transactional
    public void delete(String barcode) {
        // TODO: cascade order Item
        // orderItemService.deleteByBarcode(barcode);
        dao.delete(barcode);
    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo get(String barcode) throws ApiException {
        return getCheck(barcode);
    }

    @Transactional
    public List<InventoryPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(String barcode, InventoryPojo p) throws ApiException {
        if (productService.getByBarcode(p.getBarcode()) == null) {
            throw new ApiException("Product doesn't exist");
        }
        if (p.getQuantity() < 0) {
            throw new ApiException("Quantity can not be negative");
        }
        InventoryPojo ex = getCheck(barcode);
        ex.setQuantity(p.getQuantity());
        dao.update(ex);
    }

    @Transactional
    public InventoryPojo getCheck(String barcode) throws ApiException {
        InventoryPojo p = dao.select(barcode);

        if (p == null) {
            throw new ApiException("Inventory with given ID does not exit, id: " + barcode);
        }
        return p;
    }

    @Transactional
    public InventoryForm convert(InventoryPojo p) throws ApiException {
        InventoryForm d = new InventoryForm();
        d.setQuantity(p.getQuantity());
        d.setBarcode(p.getBarcode());
        String name = productService.getByBarcode(d.getBarcode()).getName();
        d.setName(name);
        return d;
    }


}
