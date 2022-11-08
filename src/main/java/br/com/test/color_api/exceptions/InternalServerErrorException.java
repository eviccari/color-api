package br.com.test.color_api.exceptions;

public class InternalServerErrorException extends Exception implements HTTPException<Integer>{

    private static final Integer STATUS_CODE = 500;

    public InternalServerErrorException(Throwable e) {
        super(e);
    }

    @Override
    public Integer getStatusCode() {
        return STATUS_CODE;
    }
}
