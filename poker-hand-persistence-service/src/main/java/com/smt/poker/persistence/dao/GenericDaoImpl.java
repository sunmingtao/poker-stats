package com.smt.poker.persistence.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.smt.poker.persistence.dao.util.GenericUtils;

public abstract class GenericDaoImpl<T> implements GenericDao<T> {

	@SuppressWarnings("unchecked")
	private Class<T> entityClass 
		= GenericUtils.getSuperClassGenericType(this.getClass());

	@PersistenceContext
	protected EntityManager em;

	@Transactional
	public void save(T entity) {
		em.persist(entity);
	}

	@Transactional
	public void saveOrUpdate(T entity) {
		em.merge(entity);
	}
	
	public T find(Serializable entityId) {
        return em.find(entityClass, entityId);
    }
}
