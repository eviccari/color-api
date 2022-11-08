package br.com.test.color_api.exceptions;

public interface HTTPException <T>{
    T getStatusCode();
    String getMessage();
}
