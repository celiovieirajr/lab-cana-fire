package monitor.lab.cana_fire.service;

import monitor.lab.cana_fire.domain.Alert;
import monitor.lab.cana_fire.domain.Hotspot;
import monitor.lab.cana_fire.dto.AlertResponseDto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

public interface AlertService {
    AlertResponseDto createAlert(Hotspot hotspot);
    void sendAlertEmail(AlertResponseDto alert);
    AlertResponseDto updateAlert(UUID uuid, Alert alert);
    void deleteAlert(UUID id);
    ByteArrayInputStream exportAlerts() throws IOException;
}
