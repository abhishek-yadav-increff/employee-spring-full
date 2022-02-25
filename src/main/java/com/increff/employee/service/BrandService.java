package com.increff.employee.service;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.increff.employee.dao.BrandDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.StringUtil;

@Service
public class BrandService {

    @Autowired
    private BrandDao dao;

    @Autowired
    private ProductService productService;

    @Transactional(rollbackOn = ApiException.class)
    public void add(BrandPojo p) throws ApiException {
        normalize(p);
        if (dao.checkIfExists(p)) {
            throw new ApiException("Same brand-category already exists!");
        }
        if (StringUtil.isEmpty(p.getBrand()) || StringUtil.isEmpty(p.getCategory())) {
            throw new ApiException("Brand and Category cannot be empty!");
        }
        dao.insert(p);
    }

    @Transactional
    public void delete(int id) throws ApiException {
        List<ProductPojo> productPojos = productService.selectFromBrandId(id);
        for (ProductPojo p : productPojos) {
            productService.delete(p.getId());
        }
        dao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<BrandPojo> getAll() throws ApiException {
        List<BrandPojo> p = dao.selectAll();
        if (p == null) {
            throw new ApiException("No brand-category in database!");
        }
        return dao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, BrandPojo p) throws ApiException {
        normalize(p);
        if (dao.checkIfExists(p)) {
            throw new ApiException("Same brand-category already exists!");
        }
        BrandPojo ex = getCheck(id);
        ex.setBrand(p.getBrand());
        ex.setCategory(p.getCategory());
        dao.update(ex);
    }

    @Transactional
    public BrandPojo getCheck(int id) throws ApiException {
        BrandPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Brand with given ID does not exist, id: " + id);
        }
        return p;
    }

    protected static void normalize(BrandPojo p) {
        p.setBrand(StringUtil.toLowerCase(p.getBrand()));
        p.setCategory(StringUtil.toLowerCase(p.getCategory()));
    }

    public List<BrandPojo> getByCategory(String category) throws ApiException {
        List<BrandPojo> p = dao.selectByCategory(category);
        if (p == null) {
            throw new ApiException("Brand with given category:" + category + " does not exist");
        }
        return p;
    }

    public List<BrandPojo> getByBrand(String brand) throws ApiException {
        List<BrandPojo> p = dao.selectByBrand(brand);
        if (p == null) {
            throw new ApiException("Brand Pair with given brand:" + brand + " does not exist");
        }
        return p;
    }

    public BrandPojo getByBrandAndCategory(String brand, String category) throws ApiException {
        if (brand == null) {
            throw new ApiException("Brand can not be empty!");
        }
        if (category == null) {
            throw new ApiException("Category can not be empty!");
        }
        BrandPojo p = dao.selectByBrandAndCategory(brand, category);
        if (p == null) {
            throw new ApiException("Brand Category Pair does not exist");
        }
        return p;
    }

    public List<BrandPojo> getListByBrandAndCategory(String brand, String category)
            throws ApiException {
        List<BrandPojo> p;
        if (((brand == null) || brand.isEmpty()) && ((category == null) || category.isEmpty())) {
            p = getAll();
        } else if ((brand == null) || brand.isEmpty()) {
            p = getByCategory(category);
        } else if ((category == null) || category.isEmpty()) {
            p = getByBrand(brand);
        } else {
            p = Arrays.asList(getByBrandAndCategory(brand, category));
        }
        return p;
    }
}
