package com.increff.employee.service;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.increff.employee.dao.BrandDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.util.StringUtil;

@Service
public class BrandService {

    @Autowired
    private BrandDao dao;


    @Transactional(rollbackOn = ApiException.class)
    public void add(BrandPojo p) throws ApiException {
        if (dao.checkIfExists(p)) {
            throw new ApiException("Same brand-category already exists!");
        }
        if (StringUtil.isEmpty(p.getBrand()) && StringUtil.isEmpty(p.getCategory())) {
            throw new ApiException("Brand and Category cannot be empty!");
        } else if (StringUtil.isEmpty(p.getBrand())) {
            throw new ApiException("Brand cannot be empty!");
        } else if (StringUtil.isEmpty(p.getCategory())) {
            throw new ApiException("Category cannot be empty!");
        }
        dao.insert(p);
    }

    public BrandPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    public List<BrandPojo> getAll() throws ApiException {
        List<BrandPojo> brandPojos = dao.selectAll();
        if (brandPojos == null) {
            throw new ApiException("No brand-category in database!");
        }
        return brandPojos;
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, BrandPojo p) throws ApiException {
        if (dao.checkIfExists(p)) {
            throw new ApiException("Same brand-category already exists!");
        }
        BrandPojo ex = getCheck(id);
        ex.setBrand(p.getBrand());
        ex.setCategory(p.getCategory());
        dao.update(ex);
    }

    public Boolean checkIfAlreadyExists(BrandPojo brandPojo) {
        BrandPojo brandPojo2 =
                dao.selectByBrandAndCategory(brandPojo.getBrand(), brandPojo.getCategory());
        if (brandPojo2 == null)
            return false;
        else
            return true;
    }

    public BrandPojo getCheck(int id) throws ApiException {
        BrandPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Brand with given ID does not exist, id: " + id);
        }
        return p;
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
