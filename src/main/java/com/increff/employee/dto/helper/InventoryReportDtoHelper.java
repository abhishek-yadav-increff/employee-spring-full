package com.increff.employee.dto.helper;

import com.increff.employee.model.InventoryReportData;
import com.increff.employee.pojo.InventoryPojo;

/**
 * InventoryReportDtoHelper
 */
public class InventoryReportDtoHelper {



    public static InventoryReportData convert(InventoryPojo inventoryPojo, String brand,
            String category, String name) {
        InventoryReportData p = new InventoryReportData();
        p.setBarcode(inventoryPojo.getBarcode());
        p.setQuantity(inventoryPojo.getQuantity());
        p.setBrand(brand);
        p.setCategory(category);
        p.setName(name);
        return p;
    }
}
