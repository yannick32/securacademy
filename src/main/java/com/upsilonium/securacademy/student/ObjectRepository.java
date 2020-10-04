package com.upsilonium.securacademy.student;

import java.util.List;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
public interface ObjectRepository<T> {
    public void save(T t);
    public T findById(Long id);
    public List<T> retrieveAll();
    public T delete(Long id);
}
