package monitor.lab.cana_fire.mapper;

import monitor.lab.cana_fire.domain.Alert;
import monitor.lab.cana_fire.domain.Hotspot;
import monitor.lab.cana_fire.dto.AlertResponseDto;
import org.springframework.stereotype.Component;

@Component
public class AlertMapper {

    public AlertResponseDto toResponse(Alert alert) {
        AlertResponseDto responseDto = new AlertResponseDto();
        responseDto.setLat(alert.getLat());
        responseDto.setLon(alert.getLon());
        responseDto.setDate(alert.getDate());

        return responseDto;
    }

    public Alert createAlertFromHotspot(Hotspot hotspot) {
        Alert alert = new Alert();
        alert.setLon(hotspot.getLon());
        alert.setLat(hotspot.getLat());
        alert.setDate(hotspot.getDate().toLocalDateTime());

        return alert;
    }
}
