package com.increff.employee.dto.helper;

import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;

/**
 * InventoryDtoHelper
 */
public class InventoryDtoHelper {


    public static InventoryForm convert(InventoryPojo p, String name) {
        InventoryForm d = new InventoryForm();
        d.setQuantity(p.getQuantity());
        d.setBarcode(p.getBarcode());
        // String name = productService.getByBarcode(d.getBarcode()).getName();
        d.setName(name);
        return d;
    }

}
