package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.OrderItemPojo;

@Repository
public class OrderItemDao extends AbstractDao {

    private static final String delete_id = "DELETE FROM OrderItemPojo P WHERE ID=:id";
    private static final String delete_by_productbarcode =
            "DELETE FROM OrderItemPojo P WHERE PRODUCTBarcode=:productBarcode";
    private static final String select_id = "SELECT P FROM OrderItemPojo P WHERE ID=:id";
    private static final String select_by_order_id =
            "SELECT P FROM OrderItemPojo P WHERE ORDERID=:orderId";
    private static final String select_by_product_barcode =
            "SELECT P FROM OrderItemPojo P WHERE PRODUCTBARCODE=:productBarcode";
    private static final String select_all = "SELECT P FROM OrderItemPojo P";
    private static final String select_by_product_barcode_and_orderid =
            "SELECT P FROM OrderItemPojo P WHERE PRODUCTBARCODE=:productBarcode AND ORDERID=:orderId";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderItemPojo p) {
        em.persist(p);
    }

    public int delete(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public int deleteByProductBarcode(String productBarcode) {
        Query query = em.createQuery(delete_by_productbarcode);
        query.setParameter("productBarcode", productBarcode);
        return query.executeUpdate();
    }

    public OrderItemPojo select(int id) {
        TypedQuery<OrderItemPojo> query = getQuery(select_id, OrderItemPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<OrderItemPojo> selectAll() {
        TypedQuery<OrderItemPojo> query = getQuery(select_all, OrderItemPojo.class);
        return query.getResultList();
    }

    public void update(OrderItemPojo p) {}

    public List<OrderItemPojo> getByOrderId(int orderId) {
        TypedQuery<OrderItemPojo> query = getQuery(select_by_order_id, OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

    public List<OrderItemPojo> getByProductBarcode(String productBarcode) {
        TypedQuery<OrderItemPojo> query = getQuery(select_by_product_barcode, OrderItemPojo.class);
        query.setParameter("productBarcode", productBarcode);
        return query.getResultList();

    }

    public OrderItemPojo getByProductBarcodeAndOrderId(String productBarcode, Integer orderId) {
        TypedQuery<OrderItemPojo> query =
                getQuery(select_by_product_barcode_and_orderid, OrderItemPojo.class);
        query.setParameter("productBarcode", productBarcode);
        query.setParameter("orderId", orderId);
        return getSingle(query);
    }



}
