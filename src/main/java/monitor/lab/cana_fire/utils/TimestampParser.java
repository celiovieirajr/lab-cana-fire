package monitor.lab.cana_fire.utils;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TimestampParser {

    public static Timestamp parseToTimestamp(String input) {
        try {
            return Timestamp.valueOf(input);
        } catch (IllegalArgumentException e1) {
            try {
                LocalDate date = LocalDate.parse(input);
                return Timestamp.valueOf(date.atStartOfDay());
            } catch (DateTimeParseException e2) {
                throw new IllegalArgumentException("Formato de data inválido: " + input);
            }
        }
    }
}

