package monitor.lab.cana_fire.service;

import monitor.lab.cana_fire.model.Hotspot;
import org.springframework.stereotype.Service;

@Service
public class HotspotService {

    public void handle(Hotspot hotspot) {
        System.out.println(hotspot);
    }
}
