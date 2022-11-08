package br.com.test.color_api.repositories;

import br.com.test.color_api.adapters.SQLRepositoryAdapter;
import br.com.test.color_api.dtos.ColorDTO;
import br.com.test.color_api.exceptions.InternalServerErrorException;

public class ColorRepository implements Repository<ColorDTO>{

    private SQLRepositoryAdapter<ColorDTO> sqlRepositoryAdapter;

    public ColorRepository(SQLRepositoryAdapter<ColorDTO> sqlRepositoryAdapter){
        this.sqlRepositoryAdapter = sqlRepositoryAdapter;
    }

    @Override
    public ColorDTO findById(String id) throws InternalServerErrorException {
        var sql = "select * from products.colors where id = :id";
        return this.sqlRepositoryAdapter.findById(sql, id);
    }

    @Override
    public void create(ColorDTO entity) throws InternalServerErrorException {
        var sql = """
                    insert into products.colors (id, description, short_description)
                    values (:id, :description, :shortDescription)
                  """;
        this.sqlRepositoryAdapter.create(sql, entity);
    }

    @Override
    public int update(ColorDTO entity, String id) throws InternalServerErrorException {
        var sql = """
                    update products.colors 
                       set description       = :description,
                           short_description = :shortDescription 
                     where id                = :id
                  """;
        return this.sqlRepositoryAdapter.update(sql, entity, id);
    }

    @Override
    public void delete(String id) throws InternalServerErrorException {
        var sql = "delete from products.colors where id = :id";
        this.sqlRepositoryAdapter.delete(sql, id);
    }
}
