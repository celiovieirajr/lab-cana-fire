package monitor.lab.cana_fire.service;

import monitor.lab.cana_fire.domain.Hotspot;
import org.locationtech.jts.geom.prep.PreparedGeometry;
import org.locationtech.jts.geom.prep.PreparedGeometryFactory;
import org.springframework.core.io.Resource;
import org.geotools.geojson.geom.GeometryJSON;
import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class HotspotService {

    private final List<PreparedGeometry> farms;
    private final AlertService alertService;

    public HotspotService(@Value("classpath:geo/farm.geojson") Resource r,
                          AlertService alertService) throws IOException {
        GeometryJSON gjson = new GeometryJSON();
        try (Reader rd = new InputStreamReader(r.getInputStream())) {
            Geometry farm = gjson.read(rd);
            this.farms = List.of(new PreparedGeometryFactory().create(farm));
        }
        this.alertService = alertService;
    }


    public void handle(Hotspot hotspot) {
        farms.stream()
                .filter(pg -> pg.contains(hotspot.toPoint()))
                .findFirst()
                .ifPresent(pg -> alertService.createAlert(hotspot));
    }
}
