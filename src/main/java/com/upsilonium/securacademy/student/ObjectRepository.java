package com.upsilonium.securacademy.student;

import java.util.List;

/**
 * @author Yannick Van Ham
 * created on Sunday, 04/10/2020
 */
public interface ObjectRepository<T> {
    void save(T t);
    T findById(Long id);
    List<T> retrieveAll();
    T delete(Long id);
    T update(Long id, T t);
}
