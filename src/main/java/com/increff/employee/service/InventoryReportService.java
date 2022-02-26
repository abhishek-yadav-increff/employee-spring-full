package com.increff.employee.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.increff.employee.model.InventoryReportData;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;

@Service
public class InventoryReportService {


    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private InventoryService inventoryService;

    @Transactional(rollbackOn = ApiException.class)
    public List<InventoryReportData> get() throws ApiException {
        // List<InventoryPojo> inventoryPojos = inventoryService.getAll();
        List<InventoryReportData> inventoryReportDtos = convert(inventoryService.getAll());
        for (InventoryReportData inventoryReportDto : inventoryReportDtos) {
            ProductPojo productPojo = productService.getByBarcode(inventoryReportDto.getBarcode());
            BrandPojo brandPojo = brandService.get(productPojo.getBrand_category());
            inventoryReportDto.setBrand(brandPojo.getBrand());
            inventoryReportDto.setCategory(brandPojo.getCategory());
            inventoryReportDto.setName(productPojo.getName());
        }
        return inventoryReportDtos;
    }

    public static List<InventoryReportData> convert(List<InventoryPojo> inventoryPojos) {
        List<InventoryReportData> inventoryReportDtos = new ArrayList<InventoryReportData>();
        for (InventoryPojo inventoryPojo : inventoryPojos) {
            inventoryReportDtos.add(convert(inventoryPojo));
        }
        return inventoryReportDtos;
    }

    public static InventoryReportData convert(InventoryPojo inventoryPojo) {
        InventoryReportData p = new InventoryReportData();
        p.setBarcode(inventoryPojo.getBarcode());
        p.setQuantity(inventoryPojo.getQuantity());
        return p;
    }
}
