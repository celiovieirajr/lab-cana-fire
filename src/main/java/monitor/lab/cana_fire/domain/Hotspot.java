package monitor.lab.cana_fire.domain;

import lombok.Data;
import monitor.lab.cana_fire.utils.TimestampParser;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

import java.sql.Timestamp;

@Data
public class Hotspot {

    private double lat;
    private double lon;
    private String satelite;
    private String dateRaw;

    private Timestamp date;


    public void setDateRaw(String dateRaw) {
        this.dateRaw = dateRaw;
        this.date = TimestampParser.parseToTimestamp(dateRaw);
    }


    public Geometry toPoint() {
        GeometryFactory factory = new GeometryFactory();
        return factory.createPoint(new Coordinate(lon, lat));
    }
}
