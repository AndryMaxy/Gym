package dao;

import connection.ProxyConnection;
import dao.exception.DAOException;
import dao.exception.DuplicateInsertException;
import entity.Entity;

import java.util.List;
//TODO НУЖНА ЛИ ТАКАЯ ЖЕСТКАЯ ТИПИЗАЦИЯ?
public abstract class DAO<K, T extends Entity> {

    protected ProxyConnection connection;

    public DAO(ProxyConnection connection) {
        this.connection = connection;
    }

    public abstract List<T> getAll() throws DAOException;
    public abstract T getById(K id) throws DAOException;
    public abstract T getByLogin(String id) throws DAOException;
    public abstract int add(T entity) throws DAOException, DuplicateInsertException;
    public abstract boolean delete(K id) throws DAOException;
    public abstract boolean delete(T entity) throws DAOException;
    public abstract boolean update(T entity) throws DAOException;
}
