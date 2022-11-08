package br.com.test.color_api.controllers;

import br.com.test.color_api.dtos.ResponseErrorDTO;
import br.com.test.color_api.exceptions.HTTPException;
import br.com.test.color_api.exceptions.InternalServerErrorException;
import br.com.test.color_api.exceptions.NotFoundException;
import br.com.test.color_api.exceptions.UnprocessableEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController {

    @ExceptionHandler({InternalServerErrorException.class, NotFoundException.class, UnprocessableEntityException.class})
    public ResponseEntity<ResponseErrorDTO> handleException(HTTPException<Integer> ex){
        return new ResponseEntity<>(
            ResponseErrorDTO.builder()
                .message(ex.getMessage())
                .statusCode(ex.getStatusCode())
            .build(),
            HttpStatus.valueOf(ex.getStatusCode())
        );
    }

}
