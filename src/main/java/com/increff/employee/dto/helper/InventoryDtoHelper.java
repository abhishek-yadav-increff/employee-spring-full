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
        d.setName(name);
        return d;
    }

    public static InventoryPojo convert(InventoryForm f) {
        InventoryPojo p = new InventoryPojo();
        p.setQuantity(f.getQuantity());
        p.setBarcode(f.getBarcode());
        return p;
    }

}
