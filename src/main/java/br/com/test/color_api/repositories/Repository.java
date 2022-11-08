package br.com.test.color_api.repositories;

import br.com.test.color_api.exceptions.InternalServerErrorException;

public interface Repository<T> {
    /**
     * Find entity searching by id
     * @param id
     * @return The respective entity
     * @throws InternalServerErrorException
     */
    T findById(String id) throws InternalServerErrorException;
    void create(T entity) throws InternalServerErrorException;
    int update(T entity, String id) throws InternalServerErrorException;
    void delete(String id) throws InternalServerErrorException;
}
