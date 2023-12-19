package dao;

import java.util.List;

public interface Dao<T> {
    void create(T entity);
    void update(T entity);
    void delete(T entity);
    List<T>  getAll();
}
