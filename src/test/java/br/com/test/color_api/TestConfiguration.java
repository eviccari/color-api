package br.com.test.color_api;

import java.util.UUID;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.test.color_api.adapters.SQLRepositoryAdapter;
import br.com.test.color_api.dtos.ColorDTO;
import br.com.test.color_api.repositories.ColorRepository;
import br.com.test.color_api.services.ColorService;
import br.com.test.color_api.services.Service;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TestConfiguration {

    protected Service<ColorDTO> buildServiceInstance(SQLRepositoryAdapter<ColorDTO> sqlRepositoryAdapterStub) {
        var sqlRepositoryAdapter = sqlRepositoryAdapterStub;
        var repository = new ColorRepository(sqlRepositoryAdapter);
        return new ColorService(repository);
    }

    protected ColorDTO buildColorDTOMock() {
        return ColorDTO.builder()
            .description("any")
            .shortDescription("any")
        .build();
    }

    protected String buildRandomId() {
        return UUID.randomUUID().toString();
    }
    
}
