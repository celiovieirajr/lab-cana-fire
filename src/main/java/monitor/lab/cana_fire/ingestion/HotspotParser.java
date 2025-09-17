package monitor.lab.cana_fire.ingestion;


import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBeanBuilder;
import monitor.lab.cana_fire.domain.Hotspot;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.StringReader;

public class HotspotParser {

    private final static ColumnPositionMappingStrategy<Hotspot> strat =
            new ColumnPositionMappingStrategy<>();
    static {
        strat.setType(Hotspot.class);
        strat.setColumnMapping("lat", "lon", "satelite", "date");
    }

    public static Flux<Hotspot> parse(String csv) {
        boolean hasHeader = csv.trim().startsWith("lat,lon,satelite,data");

        return Flux.using(
                () -> new CSVReaderBuilder(new StringReader(csv.trim())).withSkipLines(hasHeader ? 1 : 0).build(),
                reader -> Flux.fromIterable(
                        new CsvToBeanBuilder<Hotspot>(reader).withMappingStrategy(strat).build().parse()),
                reader -> {
                    try {
                        reader.close();
                    } catch (IOException e ) {
                        System.out.println("Error closed CSVReaderBuilder: " + e.getMessage());
                    }
                }
        );
    }
}
