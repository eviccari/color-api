package br.com.test.color_api.controllers.v1;

import br.com.test.color_api.adapters.ColorDTOSQLRepositoryAdapter;
import br.com.test.color_api.controllers.BaseController;
import br.com.test.color_api.dtos.ColorDTO;
import br.com.test.color_api.dtos.CreateOkDTO;
import br.com.test.color_api.exceptions.BadRequestException;
import br.com.test.color_api.exceptions.InternalServerErrorException;
import br.com.test.color_api.exceptions.NotFoundException;
import br.com.test.color_api.exceptions.UnprocessableEntityException;
import br.com.test.color_api.repositories.ColorRepository;
import br.com.test.color_api.services.ColorService;
import br.com.test.color_api.services.Service;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

@RestController
@RequestMapping("/api/v1/colors")
@Slf4j
public class ColorController extends BaseController {

    @Autowired
    private DataSource dataSource;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ColorDTO> findById(@PathVariable String id) throws InternalServerErrorException, NotFoundException, BadRequestException {
        log.info(String.format("searching color by id: %s", id));
        var color = this.buildServiceInstance().findById(id);
        return new ResponseEntity<>(color, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateOkDTO> create(@RequestBody ColorDTO dto) throws InternalServerErrorException, UnprocessableEntityException {
        log.info(String.format("incoming payload: %s", dto.toString()));

        var generatedId = this.buildServiceInstance().create(dto);
        return new ResponseEntity<>(CreateOkDTO.builder()
            .generatedId(generatedId)
            .build(),
            HttpStatus.CREATED
        );
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ColorDTO> update(@RequestBody ColorDTO dto, @PathVariable String id) throws InternalServerErrorException, BadRequestException, UnprocessableEntityException, NotFoundException {
        log.info(String.format("incoming payload: %s", dto.toString()));

        this.buildServiceInstance().update(dto, id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> delete(@PathVariable String id) throws InternalServerErrorException, BadRequestException {
        log.info(String.format("deleting color by id: %s", id));

        this.buildServiceInstance().delete(id);
        return new ResponseEntity<>("delete ok", HttpStatus.OK);
    }

    private Service<ColorDTO> buildServiceInstance() {
        var sqlRepositoryAdapter = new ColorDTOSQLRepositoryAdapter(this.dataSource);
        var repository = new ColorRepository(sqlRepositoryAdapter);
        return new ColorService(repository);
    }
}
