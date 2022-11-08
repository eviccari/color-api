package br.com.test.color_api.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ColorDTO {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String shortDescription;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
