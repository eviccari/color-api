package br.com.test.color_api.dtos;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Builder
public class ResponseErrorDTO implements Serializable {

    @Getter
    @Setter
    String message;

    @Getter
    @Setter
    Integer statusCode;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
