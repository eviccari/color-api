package br.com.test.color_api.adapters;

import br.com.test.color_api.dtos.ColorDTO;
import br.com.test.color_api.exceptions.InternalServerErrorException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class ColorDTOSQLRepositoryAdapter implements SQLRepositoryAdapter<ColorDTO>{

    private DataSource dataSource;

    public ColorDTOSQLRepositoryAdapter(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ColorDTO findById(String sql, String id) throws InternalServerErrorException {
        try {
            return new NamedParameterJdbcTemplate(this.dataSource).queryForObject(
                    sql,
                    new MapSqlParameterSource().addValue("id", id),
                    BeanPropertyRowMapper.newInstance(ColorDTO.class)
            );
        } catch (EmptyResultDataAccessException erdae) {
            return null;
        } catch (DataAccessException dae) {
            throw new InternalServerErrorException(dae);
        }
    }

    @Override
    public void create(String sql, ColorDTO entity) throws InternalServerErrorException {
        try {
            new NamedParameterJdbcTemplate(this.dataSource).update(
                sql,
                new BeanPropertySqlParameterSource(entity)
            );
        } catch (DataAccessException dae) {
            throw new InternalServerErrorException(dae);
        }

    }

    @Override
    public int update(String sql, ColorDTO entity, String id) throws InternalServerErrorException {
        try {
            entity.setId(id);
            return new NamedParameterJdbcTemplate(this.dataSource).update(
                    sql,
                    new BeanPropertySqlParameterSource(entity)
            );
        } catch (DataAccessException dae) {
            throw new InternalServerErrorException(dae);
        }
    }

    @Override
    public void delete(String sql, String id) throws InternalServerErrorException {
        try {
            new NamedParameterJdbcTemplate(this.dataSource).update(
                    sql,
                    new MapSqlParameterSource().addValue("id", id)
            );
        } catch (DataAccessException dae) {
            throw new InternalServerErrorException(dae);
        }

    }
}
