package monitor.lab.cana_fire.model;

import lombok.Data;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

import java.sql.Timestamp;

@Data
public class Hotspot {

    private double lat;
    private double lon;
    private String satelite;
    private Timestamp date;


    public Geometry toPoint() {
        GeometryFactory factory = new GeometryFactory();
        return factory.createPoint(new Coordinate(lon, lat));
    }
}
