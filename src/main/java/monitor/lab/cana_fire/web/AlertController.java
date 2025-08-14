package monitor.lab.cana_fire.web;

import lombok.RequiredArgsConstructor;
import monitor.lab.cana_fire.domain.Alert;
import monitor.lab.cana_fire.repository.AlertRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alerts")
public class AlertController {
    private final AlertRepository repo;

    @GetMapping
    public List<Alert> latest() {
        return repo.findTop100ByOrderByDateDesc();
    }
}