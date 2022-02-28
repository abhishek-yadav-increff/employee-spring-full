package com.increff.employee.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import com.increff.employee.pojo.BrandPojo;

@Repository
public class BrandDao extends AbstractDao {

    private static final String DELETE_ID = "DELETE FROM BrandPojo P WHERE ID=:id";
    private static final String SELECT_ID = "SELECT P FROM BrandPojo P WHERE ID=:id";
    private static final String SELECT_ALL = "SELECT P FROM BrandPojo P";
    private static final String SELECT_BRAND = "SELECT P FROM BrandPojo P WHERE BRAND=:brand";
    private static final String SELECT_CATEGORY =
            "SELECT P FROM BrandPojo P WHERE CATEGORY=:category";
    private static final String SELECT_BRAND_CATEGORY =
            "SELECT P FROM BrandPojo P WHERE BRAND=:brand AND CATEGORY=:category";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(BrandPojo p) {
        em.persist(p);
    }

    public int delete(int id) {
        Query query = em.createQuery(DELETE_ID);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public BrandPojo select(int id) {
        TypedQuery<BrandPojo> query = getQuery(SELECT_ID, BrandPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }


    public List<BrandPojo> selectAll() {
        TypedQuery<BrandPojo> query = getQuery(SELECT_ALL, BrandPojo.class);
        return query.getResultList();
    }

    public void update(BrandPojo p) {}

    public List<BrandPojo> selectByCategory(String category) {
        TypedQuery<BrandPojo> query = getQuery(SELECT_CATEGORY, BrandPojo.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    public List<BrandPojo> selectByBrand(String brand) {

        TypedQuery<BrandPojo> query = getQuery(SELECT_BRAND, BrandPojo.class);
        query.setParameter("brand", brand);
        return query.getResultList();
    }

    public BrandPojo selectByBrandAndCategory(String brand, String category) {
        TypedQuery<BrandPojo> query = getQuery(SELECT_BRAND_CATEGORY, BrandPojo.class);
        query.setParameter("brand", brand);
        query.setParameter("category", category);
        return getSingle(query);
    }



}
