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

    private static final String delete_id = "DELETE FROM BrandPojo P WHERE ID=:id";
    private static final String select_id = "SELECT P FROM BrandPojo P WHERE ID=:id";
    private static final String select_all = "SELECT P FROM BrandPojo P";
    private static final String select_brand = "SELECT P FROM BrandPojo P WHERE BRAND=:brand";
    private static final String select_brand_category =
            "SELECT P FROM BrandPojo P WHERE BRAND=:brand AND CATEGORY=:category";
    private static final String select_category =
            "SELECT P FROM BrandPojo P WHERE CATEGORY=:category";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(BrandPojo p) {
        em.persist(p);
    }

    public int delete(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public BrandPojo select(int id) {
        TypedQuery<BrandPojo> query = getQuery(select_id, BrandPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }


    public List<BrandPojo> selectAll() {
        TypedQuery<BrandPojo> query = getQuery(select_all, BrandPojo.class);
        return query.getResultList();
    }

    public void update(BrandPojo p) {}

    public List<BrandPojo> selectByCategory(String category) {
        TypedQuery<BrandPojo> query = getQuery(select_category, BrandPojo.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    public List<BrandPojo> selectByBrand(String brand) {

        TypedQuery<BrandPojo> query = getQuery(select_brand, BrandPojo.class);
        query.setParameter("brand", brand);
        return query.getResultList();
    }

    public BrandPojo selectByBrandAndCategory(String brand, String category) {
        TypedQuery<BrandPojo> query = getQuery(select_brand_category, BrandPojo.class);
        query.setParameter("brand", brand);
        query.setParameter("category", category);
        return getSingle(query);
    }


}
