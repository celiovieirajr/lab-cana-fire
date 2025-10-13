package monitor.lab.cana_fire;

import monitor.lab.cana_fire.domain.Alert;
import monitor.lab.cana_fire.repository.AlertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class AlertController {

    @Mock
    private AlertRepository alertRepository;

    @InjectMocks
    private monitor.lab.cana_fire.web.AlertController alertController;

    @Test
    void shouldReturnLatestAlerts() {
        Alert alert = new Alert(UUID.randomUUID(), -12.71, -66.82, LocalDateTime.now());
        Mockito.when(alertRepository.findTop100ByOrderByDateDesc()).thenReturn(List.of(alert));

        List<Alert> result = alertController.showLastedHundredController();

        assertEquals(1, result.size());
        assertNotNull(result);
    }

    @Test
    void shouldReturnCreateAlert() {

    }
}


