package monitor.lab.cana_fire;

import monitor.lab.cana_fire.domain.Alert;
import monitor.lab.cana_fire.dto.AlertResponseDto;
import monitor.lab.cana_fire.mapper.AlertMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class TestAlertMapper {


    @Test
    public void shouldAllValuesPositive() {

        AlertMapper mapper = new AlertMapper();
        Alert model = new Alert(UUID.randomUUID(), -12.717600, -66.823800, LocalDateTime.now());

        AlertResponseDto response = mapper.toResponse(model);

        assertEquals(model.getLon(), response.getLon());
        assertEquals(model.getLat(), response.getLat());
        assertEquals(model.getDate(), response.getDate());
        assertNotEquals(null, response);

    }

}
