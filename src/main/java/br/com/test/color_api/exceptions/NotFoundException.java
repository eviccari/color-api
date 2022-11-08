package br.com.test.color_api.exceptions;

public class NotFoundException extends Exception implements HTTPException<Integer>{

    private static final Integer STATUS_CODE = 404;

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public Integer getStatusCode() {
        return STATUS_CODE;
    }
}
