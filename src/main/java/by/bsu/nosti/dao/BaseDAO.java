package by.bsu.nosti.dao;

import java.util.List;

import by.bsu.nosti.exception.DAOException;

public interface BaseDAO<K, E> {
    void create(E entity) throws DAOException;
    E retrieve(K key) throws DAOException;
    List<E> retrieveAll() throws DAOException;
    void update(E entity) throws DAOException;
    void delete(K key) throws DAOException;
}
