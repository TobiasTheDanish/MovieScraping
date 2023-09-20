package dat.sem3.dao;

import java.util.List;

public interface DAOInterface<T> {
    T persist(T entity);
    T update(T entity);
    void delete(T entity);
    <K> T getById(K id);
    List<T> getAll();
    <K> List<T> getByRating(K rating);
    List<T> getSortedByReleaseYear();
}