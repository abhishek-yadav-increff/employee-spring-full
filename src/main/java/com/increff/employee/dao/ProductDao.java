package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.ProductPojo;

@Repository
public class ProductDao extends AbstractDao {

    private static final String DELETE_ID = "DELETE FROM ProductPojo P WHERE ID=:id";
    private static final String SELECT_ID = "SELECT P FROM ProductPojo P WHERE ID=:id";
    private static final String SELECT_BARCODE =
            "SELECT P FROM ProductPojo P WHERE BARCODE=:barcode";
    private static final String SELECT_ALL = "SELECT P FROM ProductPojo P";
    private static final String SELECT_FROM_BRAND_ID =
            "SELECT P FROM ProductPojo P WHERE BRANDCATEGORYID=:brandId";
    private static final String DELETE_FROM_BRAND_ID =
            "DELETE FROM ProductPojo P WHERE BRANDCATEGORYID=:brandId";
    private static final String SELECT_BRANDCATEGORYID_NAME_MRP =
            "SELECT P FROM ProductPojo P WHERE BRANDCATEGORYID=:bcID AND NAME=:name AND MRP=:mrp";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(ProductPojo p) {
        em.persist(p);
    }

    public int delete(int id) {
        Query query = em.createQuery(DELETE_ID);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public ProductPojo select(int id) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_ID, ProductPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<ProductPojo> selectFromBrandId(int brandId) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_FROM_BRAND_ID, ProductPojo.class);
        query.setParameter("brandId", brandId);
        return query.getResultList();

    }

    public List<ProductPojo> deleteFromBrandId(int brandId) {
        TypedQuery<ProductPojo> query = getQuery(DELETE_FROM_BRAND_ID, ProductPojo.class);
        query.setParameter("brandId", brandId);
        return query.getResultList();

    }


    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery(SELECT_ALL, ProductPojo.class);
        return query.getResultList();
    }

    public void update(ProductPojo p) {}

    public ProductPojo selectByBarcode(String barcode) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_BARCODE, ProductPojo.class);
        query.setParameter("barcode", barcode);
        return getSingle(query);
    }

    public boolean checkIfExists(ProductPojo p) {
        TypedQuery<ProductPojo> query =
                getQuery(SELECT_BRANDCATEGORYID_NAME_MRP, ProductPojo.class);
        query.setParameter("name", p.getName());
        query.setParameter("mrp", p.getMrp());
        query.setParameter("bcID", p.getBrand_category());
        if (getSingle(query) != null) {
            return true;
        } else {
            return false;
        }
    }



}
