package com.increff.employee.dto;

import com.increff.employee.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * InventoryDto
 */
@Service
public class InventoryDto {
    @Autowired
    private InventoryService inventoryService;

}
