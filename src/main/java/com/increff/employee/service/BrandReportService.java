package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.increff.employee.pojo.BrandPojo;

@Service
public class BrandReportService {


    @Autowired
    private BrandService brandService;



    @Transactional(rollbackOn = ApiException.class)
    public List<BrandPojo> get() throws ApiException {
        return brandService.getAll();
    }

}
