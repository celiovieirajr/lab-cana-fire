package monitor.lab.cana_fire.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;

@Component
public class GetLatestHotspotUrl {

    @Value("${api.base.url}")
    private String apiBaseUrl;

    public String getLatestHotspotUrl() {
        try {
            Document doc = Jsoup.connect(apiBaseUrl).get();
            Elements links = doc.select("a[href]");

            Optional<String> latestFile = links.stream()
                    .map(link -> link.attr("href"))
                    .filter(name -> name.startsWith("focos_10min_") && name.endsWith(".csv"))
                    .max(Comparator.naturalOrder());

            return latestFile.map(name -> apiBaseUrl + name).orElseThrow(() ->
                    new RuntimeException("Nenhum arquivo encontrado"));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao acessar a página", e);
        }
    }
}
