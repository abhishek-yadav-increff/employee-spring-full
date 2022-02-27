package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import com.increff.employee.dto.helper.InventoryReportDtoHelper;
import com.increff.employee.model.InventoryReportData;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * InventoryReportDto
 */
@Service
public class InventoryReportDto {


    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Transactional
    public List<InventoryReportData> getAll() throws ApiException {
        List<InventoryPojo> inventoryPojos = inventoryService.getAll();
        List<InventoryReportData> inventoryReportDatas = new ArrayList<InventoryReportData>();
        for (InventoryPojo inventoryPojo : inventoryPojos) {
            ProductPojo productPojo = productService.getByBarcode(inventoryPojo.getBarcode());
            BrandPojo brandPojo = brandService.get(productPojo.getBrand_category());
            inventoryReportDatas.add(InventoryReportDtoHelper.convert(inventoryPojo,
                    brandPojo.getBrand(), brandPojo.getCategory(), productPojo.getName()));
        }
        return inventoryReportDatas;
    }
}
