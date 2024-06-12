package com.iconsult.userservice.GenericDao;

// Created by Affan on 12-June-22

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class GenericDao<T extends Serializable> {

    @PersistenceContext
    private EntityManager entityManager;

    public T findOne(Class<T> entityClass, final long id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> findAll(Class<T> entityClass) {
        return entityManager.createQuery("from " + entityClass.getName()).getResultList();
    }

//    public void save(T entity) {
//        entityManager.persist(entity);
//    }

    public T saveOrUpdate(T entity) {
        return entityManager.merge(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public void deleteById(Class<T> entityClass, final long entityId) {
        T entity = findOne(entityClass, entityId);
        delete(entity);
    }

    public List<T> findWithNamedQuery(String queryName, Map<String, Object> parameters) {
        TypedQuery<T> query = entityManager.createNamedQuery(queryName, (Class<T>) parameters.get("resultClass"));
        parameters.forEach(query::setParameter);
        return query.getResultList();
    }

    public List<T> findWithQuery(String jpql, Map<String, Object> parameters) {
        TypedQuery<T> query = entityManager.createQuery(jpql, (Class<T>) parameters.get("resultClass"));
        parameters.forEach(query::setParameter);
        return query.getResultList();
    }

    public T findOneWithQuery(String jpql, Map<String, Object> parameters) {
            TypedQuery<T> query = entityManager.createQuery(jpql, (Class<T>) parameters.get("resultClass"));
        for (Map.Entry<String, Object> param : parameters.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Handle no result case
            return null;
        } catch (NonUniqueResultException e) {
            // Handle multiple result case
            throw new RuntimeException("Expected single result but got multiple", e);
        }
    }
}
