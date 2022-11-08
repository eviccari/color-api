package br.com.test.color_api.services;

import br.com.test.color_api.dtos.ColorDTO;
import br.com.test.color_api.exceptions.BadRequestException;
import br.com.test.color_api.exceptions.InternalServerErrorException;
import br.com.test.color_api.exceptions.NotFoundException;
import br.com.test.color_api.exceptions.UnprocessableEntityException;
import br.com.test.color_api.repositories.Repository;
import br.com.test.color_api.utils.ObjectUtils;
import br.com.test.color_api.utils.StringUtils;

public class ColorService implements Service<ColorDTO>{

    private Repository<ColorDTO> repository;

    public ColorService(Repository<ColorDTO> repository){
        this.repository = repository;
    }

    @Override
    public ColorDTO findById(String id) throws InternalServerErrorException, NotFoundException {
        var color = this.repository.findById(id);
        if(color != null) {
            return color;
        } else {
            throw new NotFoundException("not found");
        }
    }

    @Override
    public String create(ColorDTO entity) throws InternalServerErrorException, UnprocessableEntityException {
        var color = ObjectUtils.convertFrom(entity);
        color.generateId();
        color.validate();
        entity.setId(color.getId());

        this.repository.create(entity);
        return color.getId();
    }

    @Override
    public int update(ColorDTO entity, String id) throws InternalServerErrorException, UnprocessableEntityException, BadRequestException, NotFoundException {
        if(StringUtils.isEmpty(id)){
            throw new BadRequestException("color id is required");
        }

        entity.setId(id);
        var color = ObjectUtils.convertFrom(entity);
        color.validate();

        var rowCount = this.repository.update(entity, id);

        if(rowCount > 0) {
            return rowCount;
        } else {
            throw new NotFoundException("not found");
        }
    }

    @Override
    public void delete(String id) throws InternalServerErrorException, BadRequestException {
        if(StringUtils.isEmpty(id)){
            throw new BadRequestException("color id is required");
        }

        this.repository.delete(id);
    }
}
