package com.increff.employee.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.increff.employee.dto.InventoryReportDto;
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
    public List<InventoryReportDto> get() throws ApiException {
        // List<InventoryPojo> inventoryPojos = inventoryService.getAll();
        List<InventoryReportDto> inventoryReportDtos = convert(inventoryService.getAll());
        for (InventoryReportDto inventoryReportDto : inventoryReportDtos) {
            ProductPojo productPojo = productService.getByBarcode(inventoryReportDto.getBarcode());
            BrandPojo brandPojo = brandService.get(productPojo.getBrand_category());
            inventoryReportDto.setBrand(brandPojo.getBrand());
            inventoryReportDto.setCategory(brandPojo.getCategory());
        }
        return inventoryReportDtos;
    }

    public static List<InventoryReportDto> convert(List<InventoryPojo> inventoryPojos) {
        List<InventoryReportDto> inventoryReportDtos = new ArrayList<InventoryReportDto>();
        for (InventoryPojo inventoryPojo : inventoryPojos) {
            inventoryReportDtos.add(convert(inventoryPojo));
        }
        return inventoryReportDtos;
    }

    public static InventoryReportDto convert(InventoryPojo inventoryPojo) {
        InventoryReportDto p = new InventoryReportDto();
        p.setBarcode(inventoryPojo.getBarcode());
        p.setQuantity(inventoryPojo.getQuantity());
        return p;
    }
}
