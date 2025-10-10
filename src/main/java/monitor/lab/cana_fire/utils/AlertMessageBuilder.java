package monitor.lab.cana_fire.utils;

import monitor.lab.cana_fire.dto.AlertResponseDto;

import java.util.Locale;

public class AlertMessageBuilder {

    public static String buildEmailMessage(AlertResponseDto a) {
        return String.format(Locale.US,
                """
                🚨 Atenção!
    
                Um foco de calor foi detectado nas coordenadas:
                📍 Latitude: %.4f
                📍 Longitude: %.4f
                📅 Data: %s
    
                🔗 Visualize no mapa:
                https://maps.google.com/?q=%.6f,%.6f
    
                FIQUE ATENTO E TOME AS MEDIDAS NECESSÁRIAS!
                """,
                a.getLat(), a.getLon(), a.getDate(), a.getLat(), a.getLon());
    }
}