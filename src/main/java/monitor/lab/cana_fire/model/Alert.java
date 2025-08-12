package monitor.lab.cana_fire.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity @Table(name = "alerts")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Alert {

    @Id @GeneratedValue
    UUID id;
    private double lat;
    private double lon;
    private LocalDateTime date;
}
