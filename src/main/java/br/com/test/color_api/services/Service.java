package br.com.test.color_api.services;

import br.com.test.color_api.exceptions.BadRequestException;
import br.com.test.color_api.exceptions.InternalServerErrorException;
import br.com.test.color_api.exceptions.NotFoundException;
import br.com.test.color_api.exceptions.UnprocessableEntityException;

public interface Service<T> {
    T findById(String id) throws InternalServerErrorException, NotFoundException, BadRequestException;
    String create(T entity) throws InternalServerErrorException, UnprocessableEntityException;
    int update(T entity, String id) throws InternalServerErrorException, UnprocessableEntityException, BadRequestException, NotFoundException;
    void delete(String id) throws InternalServerErrorException, BadRequestException;
}
