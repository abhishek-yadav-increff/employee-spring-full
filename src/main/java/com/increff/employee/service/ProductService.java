package com.increff.employee.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.StringUtil;

@Service
public class ProductService {

    @Autowired
    private ProductDao dao;

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private BrandService brandService;


    @Transactional(rollbackOn = ApiException.class)
    public void add(ProductForm f) throws ApiException {

        ProductPojo p = convert(f);
        if (dao.checkIfExists(p)) {
            throw new ApiException("Same product already exists!");
        }
        if (p.getMrp() <= 0) {
            throw new ApiException("MRP must be positive!");
        }
        if (brandDao.select(p.getBrand_category()) == null) {
            throw new ApiException("Brand doesn't exist!");
        }
        normalize(p);
        if (StringUtil.isEmpty(p.getName())) {
            throw new ApiException("Name cannot be empty!");
        }
        dao.insert(p);
    }

    @Transactional
    public void delete(int id) throws ApiException {
        ProductPojo p = getCheck(id);
        inventoryService.delete(p.getBarcode());
        dao.delete(id);
        // orderItemDao.deleteFromProductId(id);
    }

    @Transactional
    public void deleteFromBrandId(int brandId) {
        dao.deleteFromBrandId(brandId);
    }


    @Transactional(rollbackOn = ApiException.class)
    public ProductPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<ProductPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional
    public ProductPojo getByBarcode(String barcode) throws ApiException {
        ProductPojo p = dao.selectByBarcode(barcode);
        if (p == null) {
            throw new ApiException("Product by given barcode doesn't exist!");
        }
        return p;
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, ProductForm f) throws ApiException {
        ProductPojo p = convert(f);
        normalize(p);
        if (p.getMrp() <= 0) {
            throw new ApiException("MRP must be positive!");
        }
        if (brandDao.select(p.getBrand_category()) == null) {
            throw new ApiException("Brand doesn't exist");
        }
        if (dao.checkIfExists(p)) {
            throw new ApiException("Same product already exists!");
        }
        ProductPojo ex = getCheck(id);
        ex.setBarcode(p.getBarcode());
        ex.setMrp(p.getMrp());
        ex.setBrand_category(p.getBrand_category());
        ex.setName(p.getName());
        dao.update(ex);
    }

    @Transactional
    public ProductPojo getCheck(int id) throws ApiException {
        ProductPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Product with given ID does not exit, id: " + id);
        }
        return p;
    }

    protected static void normalize(ProductPojo p) {
        p.setName(StringUtil.toLowerCase(p.getName()));
    }

    public List<ProductPojo> selectFromBrandId(int id) {
        return dao.selectFromBrandId(id);
    }

    public ProductData convert(ProductPojo p) throws ApiException {
        ProductData d = new ProductData();
        BrandPojo brandPojo = brandService.get(p.getBrand_category());
        d.setBrand(brandPojo.getBrand());
        d.setCategory(brandPojo.getCategory());
        d.setBarcode(p.getBarcode());
        d.setId(p.getId());
        d.setName(p.getName());
        d.setMrp(p.getMrp());
        System.out.println(p.getBarcode());
        System.out.println(p.getId());
        return d;
    }

    public ProductPojo convert(ProductForm f) throws ApiException {
        ProductPojo p = new ProductPojo();
        BrandPojo brandPojo = brandService.getByBrandAndCategory(f.getBrand(), f.getCategory());
        if (brandPojo == null) {
            throw new ApiException("Brand-Category pair doesn't exist!");
        }
        if (f.getName() == null) {
            throw new ApiException("Name can not be empty!");
        }
        if (f.getMrp() == null) {
            throw new ApiException("MRP can not be empty!");
        }
        p.setBrand_category(brandPojo.getId());
        p.setBarcode(f.getBarcode());
        p.setName(f.getName());

        BigDecimal bd = new BigDecimal(f.getMrp()).setScale(2, RoundingMode.HALF_DOWN);
        Double newDouble = bd.doubleValue();
        p.setMrp(newDouble);

        return p;
    }
}
