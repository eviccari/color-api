package br.com.test.color_api.utils;

import br.com.test.color_api.dtos.ColorDTO;
import br.com.test.color_api.models.Color;

public class ObjectUtils {

    private ObjectUtils(){}

    public static final Color convertFrom(ColorDTO colorDTO){
        return Color.builder()
            .description(colorDTO.getDescription())
            .shortDescription(colorDTO.getShortDescription())
            .id(colorDTO.getId())
        .build();
    }

}
