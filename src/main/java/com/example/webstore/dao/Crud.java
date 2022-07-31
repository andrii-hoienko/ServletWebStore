package com.example.webstore.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Crud<T> {
    T create (T entity) throws SQLException;
    Optional<T> findById(int id) throws Exception;
    List<T> findAll();
    void update(T entity);
}