package com.increff.employee.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * OrderXmlForm
 */
@XmlRootElement
public class OrderXmlForm {

    private Integer id;
    private String date;
    private String total;
    private List<OrderItemForm> items;

    @XmlElement
    public Integer getId() {
        return id;
    }

    @XmlElement
    public List<OrderItemForm> getItems() {
        return items;
    }

    public void setItems(List<OrderItemForm> items) {
        this.items = items;
    }

    @XmlElement
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
