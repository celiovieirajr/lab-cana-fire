package monitor.lab.cana_fire.ingestion;

import monitor.lab.cana_fire.service.HotspotService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;


@Component
public class HotspotDownloader {

    @Autowired
    private WebClient webClient;

    @Autowired
    private HotspotService hotspotService;

    private static final Logger logger = LoggerFactory.getLogger(HotspotDownloader.class);

    @Value("${api.base.url}")
    private String apiBaseUrl;

    @Scheduled(fixedDelayString = "${api.fixed.delay}")
    public void downloaderHotspot() {

        String dinamicUrl = getLatestHotspotUrl();

        logger.info(dinamicUrl);

        webClient.get()
                .uri(dinamicUrl)
                .retrieve()
                .bodyToFlux(String.class)
                .flatMap(HotspotParser::parse)
                .subscribe(hotspotService::handle);
    }


    private String getLatestHotspotUrl() {
        try {
            Document doc = Jsoup.connect(apiBaseUrl).get();
            Elements links = doc.select("a[href]");

            Optional<String> latestFile = links.stream()
                    .map(link -> link.attr("href"))
                    .filter(name -> name.startsWith("focos_10min_") && name.endsWith(".csv"))
                    .max(Comparator.naturalOrder());

            return latestFile.map(name -> apiBaseUrl + name).orElseThrow(()
                    -> new RuntimeException("Nenhum arquivo encontrado"));
    } catch (IOException e) {
            throw new RuntimeException("Erro ao acessar a página", e);
        }
    }
}
