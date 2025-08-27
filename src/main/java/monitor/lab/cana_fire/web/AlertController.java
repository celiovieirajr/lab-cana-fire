package monitor.lab.cana_fire.web;

import lombok.RequiredArgsConstructor;
import monitor.lab.cana_fire.domain.Alert;
import monitor.lab.cana_fire.domain.Hotspot;
import monitor.lab.cana_fire.repository.AlertRepository;
import monitor.lab.cana_fire.service.AlertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alerts")
public class AlertController {
    private final AlertRepository repo;
    private final AlertService service;

    @GetMapping("/latest")
    public List<Alert> latest() {
        return repo.findTop100ByOrderByDateDesc();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alert> getByIdController(@PathVariable UUID id) {
        Alert entity = service.getAlertById(id);
        return ResponseEntity.ok(entity);
    }

    @GetMapping
    public ResponseEntity<List<Alert>> getAllAlertsController() {
        List<Alert> alerts = service.getAllAlerts();
        return ResponseEntity.ok(alerts);
    }

    @PostMapping
    public ResponseEntity<Alert> createAlertController(@RequestBody Hotspot hotspot) {
        Alert created = service.createAlert(hotspot);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alert> updateAlertController(@PathVariable UUID id,
                                                       @RequestBody Alert alert) {
        Alert response = service.updateAlert(id, alert);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlertController(@PathVariable UUID id) {
        service.deleteAlert(id);
        return ResponseEntity.noContent().build();
    }
}