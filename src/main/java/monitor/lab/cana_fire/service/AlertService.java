package monitor.lab.cana_fire.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import monitor.lab.cana_fire.domain.Alert;
import monitor.lab.cana_fire.domain.Hotspot;
import monitor.lab.cana_fire.repository.AlertRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AlertService {

    private final AlertRepository repo;
    private final EmailService emailService;

    public Alert createAlert(Hotspot hotspot) {
        Alert alert = new Alert(
                null,
                hotspot.getLat(),
                hotspot.getLon(),
                hotspot.getDate().toLocalDateTime()
        );
        repo.save(alert);
        emailService.notify(alert);

        return alert;
        }

        public Alert getAlertById(UUID id) {
        return repo.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id não foi encontrado"));
        }

        public List<Alert> getAllAlerts() {
            List<Alert> alerts = new ArrayList<>();
            repo.findAll().forEach(alerts::add);
            return alerts;
        }

        public Alert updateAlert(UUID uuid, Alert alert) {
            Alert entity = repo.findById(uuid)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alerts não existe"));

            entity.setDate(alert.getDate());
            entity.setLon(alert.getLon());
            entity.setLat(alert.getLat());

            Alert entitySaved = repo.save(entity);

            return entitySaved;
        }

        public void deleteAlert(UUID id) {
            Alert entity = repo.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alerts não existe"));
            repo.delete(entity);
        }
}
