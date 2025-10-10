package monitor.lab.cana_fire.service;

import monitor.lab.cana_fire.domain.Hotspot;
import monitor.lab.cana_fire.dto.AlertResponseDto;
import org.locationtech.jts.geom.prep.PreparedGeometry;
import org.locationtech.jts.geom.prep.PreparedGeometryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.geotools.geojson.geom.GeometryJSON;
import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class HotspotService {

    private final List<PreparedGeometry> farms;
    private final AlertServiceImpl alertService;

    private final static Logger log = LoggerFactory.getLogger(AlertServiceImpl.class);

    public HotspotService(@Value("classpath:geo/*.geojson") Resource[] resources,
                          AlertServiceImpl alertService) throws IOException {
        GeometryJSON gjson = new GeometryJSON();
        List<PreparedGeometry> geometries = new ArrayList<>();

        for (Resource r : resources) {
            try (Reader rd = new InputStreamReader(r.getInputStream())) {
                Geometry farm = gjson.read(rd);
                geometries.add(new PreparedGeometryFactory().create(farm));
            }
        }

        this.farms = geometries;
        this.alertService = alertService;
    }


    public void handle(Hotspot hotspot) {
        farms.stream()
                .filter(pg -> pg.contains(hotspot.toPoint()))
                .findFirst()
                .ifPresent(pg -> {
                    AlertResponseDto responseDto = alertService.createAlert(hotspot);

                    log.info(String.valueOf(responseDto));
                    alertService.sendAlertEmail(responseDto);
                });
    }
}
