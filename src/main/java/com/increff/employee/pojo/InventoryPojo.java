package com.increff.employee.pojo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Entity
public class InventoryPojo implements Serializable {

    @Id
    private String barcode;

    @Min(value = 0)
    private Integer quantity;


    public Integer getQuantity() {
        return quantity;
    }



    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
