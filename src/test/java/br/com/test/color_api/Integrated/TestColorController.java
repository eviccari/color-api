package br.com.test.color_api.Integrated;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;

import br.com.test.color_api.TestConfiguration;
import br.com.test.color_api.dtos.CreateOkDTO;

@Sql({"/create-table.sql"})
@RunWith(SpringRunner.class)
public class TestColorController extends TestConfiguration{

    public static final String BASE_PATH = "/api/v1/colors/";

    @Autowired
    private MockMvc mvc;

    @Test
    void ShouldFindColorById() throws Exception{
        var dto = this.buildColorDTOMock();

        var request = MockMvcRequestBuilders.post(
            BASE_PATH
        ).contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(dto.toString());

        var response = mvc.perform(request).andReturn();
        var generatedId = new Gson().fromJson(
            response.getResponse().getContentAsString(), 
            CreateOkDTO.class
        );

        request = MockMvcRequestBuilders.get(BASE_PATH + generatedId.getGeneratedId());
        response = mvc.perform(request).andReturn();
        assertEquals(200, response.getResponse().getStatus());
    }

    @Test
    void ShouldGet404HTTPStatus() throws Exception {
        var request = MockMvcRequestBuilders.get(BASE_PATH + "100");
        var response = mvc.perform(request).andReturn();
        assertEquals(404, response.getResponse().getStatus());
    }

    @Test
    void ShouldCreateNewColor() throws Exception {
        var request = MockMvcRequestBuilders.post(
            BASE_PATH
        ).contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(this.buildColorDTOMock().toString());

        var response = mvc.perform(request).andReturn();
        assertEquals(201, response.getResponse().getStatus());
    }

    @Test
    void ShouldUpdateColor() throws Exception {
        var dto = this.buildColorDTOMock();

        var request = MockMvcRequestBuilders.post(
            BASE_PATH
        ).contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(dto.toString());

        var response = mvc.perform(request).andReturn();
        var generatedId = new Gson().fromJson(
            response.getResponse().getContentAsString(), 
            CreateOkDTO.class
        );

        dto.setId(generatedId.getGeneratedId());
        dto.setDescription("new description");

        request = MockMvcRequestBuilders.put(
            BASE_PATH + dto.getId()
        ).contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(dto.toString());

        response = mvc.perform(request).andReturn();
        assertEquals(200, response.getResponse().getStatus());
    }

    @Test
    void ShouldDeleteColor() throws Exception {
        var dto = this.buildColorDTOMock();

        var request = MockMvcRequestBuilders.post(
            BASE_PATH
        ).contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(dto.toString());

        var response = mvc.perform(request).andReturn();
        var generatedId = new Gson().fromJson(
            response.getResponse().getContentAsString(), 
            CreateOkDTO.class
        );

        request = MockMvcRequestBuilders.delete(BASE_PATH + generatedId.getGeneratedId());
        response = mvc.perform(request).andReturn();

        assertEquals(200, response.getResponse().getStatus());

        request = MockMvcRequestBuilders.get(BASE_PATH + generatedId.getGeneratedId());
        response = mvc.perform(request).andReturn();
        assertEquals(404, response.getResponse().getStatus());
    }
    
}
