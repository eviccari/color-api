package br.com.test.color_api.models;

import br.com.test.color_api.exceptions.UnprocessableEntityException;
import br.com.test.color_api.utils.StringUtils;
import com.google.gson.Gson;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
public class Color implements Validatable, Identifiable{

    @Getter
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

    @Override
    public void validate() throws UnprocessableEntityException {
        if(StringUtils.isEmpty(this.id))
            throw new UnprocessableEntityException("id is required");

        if(StringUtils.isEmpty(this.description))
            throw new UnprocessableEntityException("description is required");

        if(StringUtils.isEmpty(this.shortDescription))
            throw new UnprocessableEntityException("shortDescription is required");

    }

    @Override
    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }
}
