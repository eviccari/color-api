package br.com.test.color_api.adapters;

import br.com.test.color_api.exceptions.InternalServerErrorException;

public interface SQLRepositoryAdapter<T> {
    T findById(String sql, String id) throws InternalServerErrorException;
    void create(String sql, T entity) throws InternalServerErrorException;
    int update(String sql, T entity, String id) throws InternalServerErrorException;
    void delete(String sql, String id) throws InternalServerErrorException;
}
