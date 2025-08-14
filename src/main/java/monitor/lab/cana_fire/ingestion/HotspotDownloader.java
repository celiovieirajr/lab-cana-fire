package monitor.lab.cana_fire.ingestion;


import lombok.AllArgsConstructor;
import monitor.lab.cana_fire.service.HotspotService;
import monitor.lab.cana_fire.util.HotspotUrlGenerator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@AllArgsConstructor
public class HotspotDownloader {

    private final WebClient webClient;
    private final HotspotService hotspotService;

    @Scheduled(fixedDelayString = "${api.fixed.delay}")
    public void downloaderHotspot() {

        String dinamicUrl = HotspotUrlGenerator.generateLatestUrl();
        System.out.println(dinamicUrl);

        webClient.get()
                .uri(dinamicUrl)
                .retrieve()
                .bodyToFlux(String.class)
                .flatMap(HotspotParser::parse)
                .subscribe(hotspotService::handle);
    }
}
