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

    private static final String DELETE_BARCODE =
            "DELETE FROM InventoryPojo P WHERE BARCODE=:barcode";
    private static final String SELECT_BARCODE =
            "SELECT P FROM InventoryPojo P WHERE BARCODE=:barcode";
    private static final String SELECT_ALL = "SELECT P FROM InventoryPojo P";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(InventoryPojo p) {
        em.persist(p);
    }

    @Transactional
    public int delete(String barcode) {
        Query query = em.createQuery(DELETE_BARCODE);
        query.setParameter("barcode", barcode);
        return query.executeUpdate();
    }

    @Transactional
    public InventoryPojo select(String barcode) {
        TypedQuery<InventoryPojo> query = getQuery(SELECT_BARCODE, InventoryPojo.class);
        query.setParameter("barcode", barcode);
        return getSingle(query);
    }

    @Transactional
    public List<InventoryPojo> selectAll() {
        TypedQuery<InventoryPojo> query = getQuery(SELECT_ALL, InventoryPojo.class);
        return query.getResultList();
    }

    public void update(InventoryPojo p) {}



}
