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

    private static final String DELETE_ID = "DELETE FROM OrderPojo P WHERE ID=:id";
    private static final String SELECT_ID = "SELECT P FROM OrderPojo P WHERE ID=:id";
    private static final String SELECT_ALL = "SELECT P FROM OrderPojo P";
    private static final String SELECT_TIME =
            "SELECT P FROM OrderPojo P WHERE ISCOMPLETED=1 AND TIMEMILIS BETWEEN :startTime AND :endTime";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderPojo p) {
        em.persist(p);
    }

    public int delete(int id) {
        Query query = em.createQuery(DELETE_ID);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public OrderPojo select(int id) {
        TypedQuery<OrderPojo> query = getQuery(SELECT_ID, OrderPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<OrderPojo> selectAll() {
        TypedQuery<OrderPojo> query = getQuery(SELECT_ALL, OrderPojo.class);
        return query.getResultList();
    }

    public void update(OrderPojo p) {}

    public List<OrderPojo> selectByTime(long time, long time2) {
        TypedQuery<OrderPojo> query = getQuery(SELECT_TIME, OrderPojo.class);
        query.setParameter("startTime", time);
        query.setParameter("endTime", time2);
        return query.getResultList();

    }



}
