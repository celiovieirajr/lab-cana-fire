package monitor.lab.cana_fire.web;

import lombok.RequiredArgsConstructor;
import monitor.lab.cana_fire.repository.AlertRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UiController {
    private final AlertRepository repository;

    @GetMapping("/")
    public String home(Model m) {
        m.addAttribute("alerts", repository.findTop100ByOrderByDateDesc());
        return "alerts";
    }
}