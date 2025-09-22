package monitor.lab.cana_fire.web;

import monitor.lab.cana_fire.domain.Alert;
import monitor.lab.cana_fire.domain.Hotspot;
import monitor.lab.cana_fire.repository.AlertRepository;
import monitor.lab.cana_fire.service.AlertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    private AlertRepository alertRepository;
    private AlertService service;

    public AlertController() {}

    public AlertController(AlertRepository alertRepository, AlertService service) {
        this.alertRepository = alertRepository;
        this.service = service;
    }

    @GetMapping("/latest")
    public List<Alert> showLastedHundredController() {
        return alertRepository.findTop100ByOrderByDateDesc();
    }


    @GetMapping
    public List<Alert> findAllController() {
        return (List<Alert>) alertRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Alert> findByIdController(@PathVariable UUID id) {
        return alertRepository.findById(id);
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