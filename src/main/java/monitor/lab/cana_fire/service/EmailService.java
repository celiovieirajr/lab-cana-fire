package monitor.lab.cana_fire.service;

import monitor.lab.cana_fire.dto.AlertResponseDto;

public interface EmailService {

    public void notify(AlertResponseDto alert);
}
