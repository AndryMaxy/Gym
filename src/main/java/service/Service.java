package service;

import connection.ConnectionPool;
import dao.exception.DuplicateInsertException;
import entity.Entity;
import service.exception.ServiceException;

import java.util.List;

public abstract class Service<K, T extends Entity> {

    protected ConnectionPool pool = ConnectionPool.getInstance();

    public Service(){}

    public abstract List<T> getAll() throws ServiceException;
    public abstract T getById(K id) throws ServiceException;
    public abstract T getByLogin(String login) throws ServiceException;
    public abstract void add(T entity) throws ServiceException, DuplicateInsertException;
    public abstract boolean delete(K id) throws ServiceException;
    public abstract boolean delete(T entity) throws ServiceException;
    public abstract boolean update(T entity) throws ServiceException;
}
