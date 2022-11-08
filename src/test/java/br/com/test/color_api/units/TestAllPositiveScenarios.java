package br.com.test.color_api.units;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.test.color_api.TestConfiguration;
import br.com.test.color_api.adapters.SQLRepositoryAdapter;
import br.com.test.color_api.dtos.ColorDTO;
import br.com.test.color_api.exceptions.BadRequestException;
import br.com.test.color_api.exceptions.InternalServerErrorException;
import br.com.test.color_api.exceptions.NotFoundException;
import br.com.test.color_api.exceptions.UnprocessableEntityException;

class TestAllPositiveScenarios extends TestConfiguration{

    private SQLRepositoryAdapter<ColorDTO> buildSQlRepositoryAdapterStub() {
        return new SQLRepositoryAdapter<ColorDTO>() {

            private List<ColorDTO> dtos = new ArrayList<>();

            @Override
            public ColorDTO findById(String sql, String id) throws InternalServerErrorException {
                var optional = dtos.stream().filter(d-> d.getId().equals(id)).findFirst();

                if (optional.isPresent()) {
                    return optional.get();
                } else {
                    return null;
                }
            }

            @Override
            public void create(String sql, ColorDTO entity) throws InternalServerErrorException {
                dtos.add(entity);
            }

            @Override
            public int update(String sql, ColorDTO entity, String id) throws InternalServerErrorException {
                for (ColorDTO colorDTO : dtos) {
                    if(colorDTO.getId().equals(id)) {
                        colorDTO.setShortDescription(entity.getDescription());
                        colorDTO.setShortDescription(entity.getShortDescription());
                        return 1;
                    }    
                }
                return 0;
            }

            @Override
            public void delete(String sql, String id) throws InternalServerErrorException {
                for (int i = 0; i < dtos.size(); i++) {
                    if(dtos.get(i).getId().equals(id)) {
                        dtos.remove(i);
                        break;
                    }
                }
            }
        };
    }

    @Test
    void ShouldCreateNewColor() throws UnprocessableEntityException, InternalServerErrorException {
        var service = this.buildServiceInstance(this.buildSQlRepositoryAdapterStub());
        var dto = this.buildColorDTOMock();

        var generatedId = service.create(dto);
        assertNotEquals(null, generatedId);
    }

    @Test
    void ShouldUpdateColor() throws UnprocessableEntityException, BadRequestException, InternalServerErrorException, NotFoundException {
        var service = this.buildServiceInstance(this.buildSQlRepositoryAdapterStub());
        var dto = this.buildColorDTOMock();

        var generatedId = service.create(dto);
        var updatedRowCount = service.update(dto, generatedId);

        assertEquals(1, updatedRowCount);
    }

    @Test
    void ShouldDeleteColor() throws BadRequestException, UnprocessableEntityException, NotFoundException,InternalServerErrorException{
        var service = this.buildServiceInstance(this.buildSQlRepositoryAdapterStub());
        var dto = this.buildColorDTOMock();

        var generatedId = service.create(dto);
        service.delete(generatedId);

        assertThrows(NotFoundException.class, () -> service.findById(generatedId));
    }

    @Test
    void ShouldFindById() throws BadRequestException, InternalServerErrorException, UnprocessableEntityException, NotFoundException {
        var service = this.buildServiceInstance(this.buildSQlRepositoryAdapterStub());
        var dto = this.buildColorDTOMock();

        var generatedId = service.create(dto);
        var found = service.findById(generatedId);
        assertEquals(dto.getDescription(), found.getDescription());
    }

}
