package monitor.lab.cana_fire.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlertResponseDto {

    private double lat;
    private double lon;
    private LocalDateTime date;
}
