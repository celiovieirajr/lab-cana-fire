package monitor.lab.cana_fire.repository;

import monitor.lab.cana_fire.model.Alert;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface AlertRepository extends CrudRepository<Alert, UUID> {
    List<Alert> findTop100ByOrderByDateDesc();

}
