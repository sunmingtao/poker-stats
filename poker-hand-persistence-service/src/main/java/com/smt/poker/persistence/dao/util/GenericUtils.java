package com.smt.poker.persistence.dao.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericUtils {
    @SuppressWarnings("rawtypes")
	public static Class getSuperClassGenericType(Class clazz){
        return getSuperClassGenericType(clazz, 0);
    }
    
    /**
     * 
     * @param clazz
     * @param index
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Class getSuperClassGenericType(Class clazz, int index){
        //Get super class's generic Type
        //e.g. GenericDaoSupport<Order>
        Type genericType = clazz.getGenericSuperclass();
        //If not a parameterized type, e.g. GenericDaoSupport
        if (!(genericType instanceof ParameterizedType)){
            //Just return the Object class
            return Object.class;
        }
        //Get actual types
        //e.g. for GenericDaoSupport<Order, Customer, Item>
        //params = {Order, Customer, Item}
        Type[] params = ((ParameterizedType)genericType)
            .getActualTypeArguments();
        if (index >= params.length || index < 0){
            throw new IllegalArgumentException
            ("The index is invalid:"+index);
        }
        return (Class)params[index];
    }
}
