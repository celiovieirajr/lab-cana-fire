package monitor.lab.cana_fire.util;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class HotspotUrlGenerator {

    public static String generateLatestUrl() {
        ZonedDateTime utcNow = ZonedDateTime.now(ZoneOffset.UTC).minusMinutes(10);
        int roundedMinute = (utcNow.getMinute() / 10) * 10;
        utcNow = utcNow.withMinute(roundedMinute).withSecond(0).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm");
        String formattedTimestamp = utcNow.format(formatter);

        return "focos_10min_" + formattedTimestamp + ".csv";
    }
}
