package monitor.lab.cana_fire;

import monitor.lab.cana_fire.domain.Alert;
import monitor.lab.cana_fire.domain.Hotspot;
import monitor.lab.cana_fire.ingestion.HotspotParser;
import monitor.lab.cana_fire.repository.AlertRepository;
import monitor.lab.cana_fire.service.AlertService;
import monitor.lab.cana_fire.service.EmailService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class CanaFireApplicationTests {


	@Test
	void testHotspotParserHeader() {
		String csv = """
            lat,lon,satelite,data
            -10.1234,-45.6789,AQUA,2025-08-11 12:59:00
            -11.5678,-46.1234,TERRA,2025-08-11 12:59:00
            """;

		Flux<Hotspot> result = HotspotParser.parse(csv);

		StepVerifier.create(result)
				.expectNextMatches(h -> h.getLat() == -10.1234 && h.getLon() == -45.6789 && h.getSatelite().equals("AQUA"))
				.expectNextMatches(h -> h.getLat() == -11.5678 && h.getLon() == -46.1234 && h.getSatelite().equals("TERRA"))
				.verifyComplete();
	}

	@Test
	void testParseCsvWithoutHeader() {
		String csv = """
            -10.1234,-45.6789,AQUA,2025-08-11 12:59:00
            -11.5678,-46.1234,TERRA,2025-08-11 12:59:00
            """;

		Flux<Hotspot> result = HotspotParser.parse(csv);

		StepVerifier.create(result)
				.expectNextCount(2)
				.verifyComplete();
	}
}
