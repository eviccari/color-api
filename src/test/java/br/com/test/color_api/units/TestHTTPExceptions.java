package br.com.test.color_api.units;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.test.color_api.TestConfiguration;
import br.com.test.color_api.adapters.SQLRepositoryAdapter;
import br.com.test.color_api.dtos.ColorDTO;
import br.com.test.color_api.exceptions.BadRequestException;
import br.com.test.color_api.exceptions.InternalServerErrorException;
import br.com.test.color_api.exceptions.NotFoundException;
import br.com.test.color_api.exceptions.UnprocessableEntityException;
import br.com.test.color_api.models.Color;

public class TestHTTPExceptions extends TestConfiguration{

    public SQLRepositoryAdapter<ColorDTO> buSqlRepositoryAdapterStub() {
        return new SQLRepositoryAdapter<ColorDTO>() {

            @Override
            public ColorDTO findById(String sql, String id) throws InternalServerErrorException {
                return null;
            }

            @Override
            public void create(String sql, ColorDTO entity) throws InternalServerErrorException {
                throw new InternalServerErrorException(new Throwable("creation_error"));
            }

            @Override
            public int update(String sql, ColorDTO entity, String id) throws InternalServerErrorException {
                return 0;
            }

            @Override
            public void delete(String sql, String id) throws InternalServerErrorException {
                throw new InternalServerErrorException(new Throwable("delete_error"));
            }            
        };
    }

    @Test
    void ShouldRiseInternalServerErrorException() {
        var dto = this.buildColorDTOMock();   

        assertThrows(InternalServerErrorException.class, 
            ()-> this.buildServiceInstance(this.buSqlRepositoryAdapterStub()).create(dto)
        );
    }

    @Test
    void ShouldRiseBadRequestException() {
        String id = null;
        var service = this.buildServiceInstance(this.buSqlRepositoryAdapterStub());

        assertThrows(BadRequestException.class, () -> service.findById(id));
        assertThrows(BadRequestException.class, () -> service.delete(id));
        assertThrows(BadRequestException.class, () -> service.update(this.buildColorDTOMock(), id));
    }

    @Test
    void ShouldRiseNotFoundException() {
        var id = this.buildRandomId();
        var service = this.buildServiceInstance(this.buSqlRepositoryAdapterStub());

        assertThrows(NotFoundException.class, ()-> service.findById(id));

        var dto = this.buildColorDTOMock();
        assertThrows(NotFoundException.class, ()-> service.update(dto, id));

    }

    @Test
    void ShouldRiseUnprocessableEntityException() {
        var dto = this.buildColorDTOMock();
        dto.setDescription(null);
        var service = this.buildServiceInstance(this.buSqlRepositoryAdapterStub());

        assertThrows(UnprocessableEntityException.class, ()-> service.create(dto));

        var modelWithBlankId = Color.builder()
            .description("any")
            .shortDescription("any")
        .build();

        assertThrows(UnprocessableEntityException.class, ()-> modelWithBlankId.validate());

        var modelWithBlankDescription = Color.builder()
            .description(null)
            .shortDescription("any")
        .build();

        modelWithBlankDescription.generateId();
        assertThrows(UnprocessableEntityException.class, ()-> modelWithBlankDescription.validate());

        var modelWithBlankShortDescription = Color.builder()
            .description("any")
            .shortDescription(null)
        .build();

        modelWithBlankShortDescription.generateId();
        assertThrows(UnprocessableEntityException.class, ()-> modelWithBlankShortDescription.validate());
    }

}
