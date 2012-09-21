package com.smt.poker.persistence.dao;

import java.io.Serializable;

public interface GenericDao<T> {
	public void save(T entity);
    public void saveOrUpdate(T entity);
    public T find(Serializable entityId);
}
