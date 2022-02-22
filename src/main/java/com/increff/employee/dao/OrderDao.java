package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.OrderPojo;

@Repository
public class OrderDao extends AbstractDao {

    private static final String delete_id = "DELETE FROM OrderPojo P WHERE ID=:id";
    private static final String select_id = "SELECT P FROM OrderPojo P WHERE ID=:id";
    private static final String select_all = "SELECT P FROM OrderPojo P";
    private static final String select_time =
            "SELECT P FROM OrderPojo P WHERE TIME BETWEEN :startTime AND :endTime";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderPojo p) {
        em.persist(p);
    }

    public int delete(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public OrderPojo select(int id) {
        TypedQuery<OrderPojo> query = getQuery(select_id, OrderPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<OrderPojo> selectAll() {
        TypedQuery<OrderPojo> query = getQuery(select_all, OrderPojo.class);
        return query.getResultList();
    }

    public void update(OrderPojo p) {}

    public List<OrderPojo> selectByTime(long time, long time2) {
        TypedQuery<OrderPojo> query = getQuery(select_time, OrderPojo.class);
        query.setParameter("startTime", time);
        query.setParameter("endTime", time2);
        return query.getResultList();

    }



}
