package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;
import com.increff.employee.dto.helper.InventoryDtoHelper;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * InventoryDto
 */
@Service
public class InventoryDto {
    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductService productService;

    public void add(InventoryForm inventoryForm) throws ApiException {
        InventoryPojo inventoryPojo = InventoryDtoHelper.convert(inventoryForm);
        inventoryService.add(inventoryPojo);
    }

    public void delete(String barcode) {
        inventoryService.delete(barcode);
    }

    public InventoryForm get(String barcode) throws ApiException {
        InventoryPojo inventoryPojo = inventoryService.get(barcode);
        ProductPojo productPojo = productService.getByBarcode(barcode);
        return InventoryDtoHelper.convert(inventoryPojo, productPojo.getName());
    }

    public List<InventoryForm> getAll() throws ApiException {
        List<InventoryPojo> inventoryPojos = inventoryService.getAll();
        List<InventoryForm> inventoryForms = new ArrayList<InventoryForm>();
        for (InventoryPojo inventoryPojo : inventoryPojos) {
            ProductPojo productPojo = productService.getByBarcode(inventoryPojo.getBarcode());
            inventoryForms.add(InventoryDtoHelper.convert(inventoryPojo, productPojo.getName()));
        }
        return inventoryForms;
    }

    public void update(String barcode, InventoryForm inventoryForm) throws ApiException {
        InventoryPojo inventoryPojo = InventoryDtoHelper.convert(inventoryForm);
        inventoryService.update(barcode, inventoryPojo);
    }


}
