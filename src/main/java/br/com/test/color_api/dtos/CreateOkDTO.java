package br.com.test.color_api.dtos;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Builder
public class CreateOkDTO implements Serializable {

    @Getter
    @Setter
    private String generatedId;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
