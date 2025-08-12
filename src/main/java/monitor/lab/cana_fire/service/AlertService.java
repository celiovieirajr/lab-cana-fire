package monitor.lab.cana_fire.service;

import lombok.RequiredArgsConstructor;
import monitor.lab.cana_fire.domain.Alert;
import monitor.lab.cana_fire.domain.Hotspot;
import monitor.lab.cana_fire.repository.AlertRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertService {

private final AlertRepository repo;

public Alert createAlert(Hotspot hotspot) {
    Alert alert = new Alert(
            null,
            hotspot.getLat(),
            hotspot.getLon(),
            hotspot.getDate().toLocalDateTime()
    );
    repo.save(alert);
    return alert;
    }
}
