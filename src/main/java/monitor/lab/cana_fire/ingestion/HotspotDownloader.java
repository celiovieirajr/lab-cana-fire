package monitor.lab.cana_fire.ingestion;

import monitor.lab.cana_fire.service.HotspotService;
import monitor.lab.cana_fire.utils.GetLatestHotspotUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;



@Component
public class HotspotDownloader {

    @Autowired
    private WebClient webClient;

    @Autowired
    private HotspotService hotspotService;

    @Autowired
    private GetLatestHotspotUrl getLastedUrl;

    private static final Logger logger = LoggerFactory.getLogger(HotspotDownloader.class);


    @Scheduled(fixedDelayString = "${api.fixed.delay}")
    public void downloaderHotspot() {

        String dinamicUrl = getLastedUrl.getLatestHotspotUrl();

        logger.info(dinamicUrl);

        webClient.get()
                .uri(dinamicUrl)
//                .uri("focos_10min_20250919_0020.csv")
                .retrieve()
                .bodyToFlux(String.class)
                .flatMap(HotspotParser::parse)
                .subscribe(hotspotService::handle);
    }
}
