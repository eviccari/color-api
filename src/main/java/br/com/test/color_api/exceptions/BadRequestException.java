package br.com.test.color_api.exceptions;

public class BadRequestException extends Exception implements HTTPException<Integer>{

    private static final Integer STATUS_CODE = 400;

    public BadRequestException(String message) {
        super(message);
    }

    @Override
    public Integer getStatusCode() {
        return STATUS_CODE;
    }
}
