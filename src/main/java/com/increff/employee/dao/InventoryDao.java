package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.InventoryPojo;

@Repository
public class InventoryDao extends AbstractDao {

    private static final String delete_barcode =
            "DELETE FROM InventoryPojo P WHERE BARCODE=:barcode";
    private static final String select_barcode =
            "SELECT P FROM InventoryPojo P WHERE BARCODE=:barcode";
    private static final String select_all = "SELECT P FROM InventoryPojo P";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(InventoryPojo p) {
        em.persist(p);
    }

    @Transactional
    public int delete(String barcode) {
        Query query = em.createQuery(delete_barcode);
        query.setParameter("barcode", barcode);
        return query.executeUpdate();
    }

    @Transactional
    public InventoryPojo select(String barcode) {
        TypedQuery<InventoryPojo> query = getQuery(select_barcode, InventoryPojo.class);
        query.setParameter("barcode", barcode);
        return getSingle(query);
    }

    @Transactional
    public List<InventoryPojo> selectAll() {
        TypedQuery<InventoryPojo> query = getQuery(select_all, InventoryPojo.class);
        return query.getResultList();
    }

    public void update(InventoryPojo p) {}



}
