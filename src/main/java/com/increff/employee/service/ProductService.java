package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.StringUtil;

@Service
public class ProductService {

    @Autowired
    private ProductDao dao;

    @Autowired
    private BrandService brandService;

    @Transactional(rollbackOn = ApiException.class)
    public void add(ProductPojo p) throws ApiException {
        if (dao.checkIfExists(p)) {
            throw new ApiException("Same product already exists!");
        }
        if (p.getMrp() <= 0) {
            throw new ApiException("MRP must be positive!");
        }
        brandService.get(p.getBrand_category());
        if (StringUtil.isEmpty(p.getName())) {
            throw new ApiException("Name cannot be empty!");
        }
        dao.insert(p);
    }

    public ProductPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    public List<ProductPojo> getAll() throws ApiException {
        return dao.selectAll();
    }

    public ProductPojo getByBarcode(String barcode) throws ApiException {
        ProductPojo p = dao.selectByBarcode(barcode);
        if (p == null) {
            throw new ApiException("Product by given barcode doesn't exist!");
        }
        return p;
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, ProductPojo p) throws ApiException {
        if (p.getMrp() <= 0) {
            throw new ApiException("MRP must be positive!");
        }
        brandService.get(p.getBrand_category());
        if (dao.checkIfExists(p)) {
            System.out.print("Same product already exists!");
            throw new ApiException("Same product already exists!");
        }
        System.out.print("Same product doesnt already exists!");

        ProductPojo ex = getCheck(id);
        ex.setBarcode(p.getBarcode());
        ex.setMrp(p.getMrp());
        ex.setBrand_category(p.getBrand_category());
        ex.setName(p.getName());
        dao.update(ex);
    }

    public ProductPojo getCheck(int id) throws ApiException {
        ProductPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Product with given ID does not exit, id: " + id);
        }
        return p;
    }

    public List<ProductPojo> selectFromBrandId(int id) {
        return dao.selectFromBrandId(id);
    }

}
