package br.com.test.color_api.models;

import br.com.test.color_api.exceptions.UnprocessableEntityException;

public interface Validatable {
    void validate() throws UnprocessableEntityException;
}
