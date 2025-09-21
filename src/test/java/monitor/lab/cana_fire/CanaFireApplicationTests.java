package monitor.lab.cana_fire;

import monitor.lab.cana_fire.domain.Alert;
import monitor.lab.cana_fire.domain.Hotspot;
import monitor.lab.cana_fire.repository.AlertRepository;
import monitor.lab.cana_fire.service.AlertService;
import monitor.lab.cana_fire.service.EmailService;
import monitor.lab.cana_fire.web.UiController;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class CanaFireApplicationTests {

	@Test
	void testAlertServiceCreateAlert() {
		AlertRepository mockRepo = Mockito.mock(AlertRepository.class);
		EmailService mockEmail = Mockito.mock(EmailService.class);
		AlertService service = new AlertService(mockRepo, mockEmail);

		Hotspot hotspot = new Hotspot();
		hotspot.setLat(-10.1234);
		hotspot.setLon(-45.6789);
		hotspot.setDate(java.sql.Timestamp.valueOf(LocalDateTime.of(2025, 8, 14, 13, 30)));

		Alert alert = service.createAlert(hotspot);

		ArgumentCaptor<Alert> captor = ArgumentCaptor.forClass(Alert.class);
		verify(mockRepo).save(captor.capture());

		Alert savedAlert = captor.getValue();
		assertEquals(-10.1234, savedAlert.getLat());
		assertEquals(-45.6789, savedAlert.getLon());
		assertEquals(LocalDateTime.of(2025, 8, 14, 13, 30), savedAlert.getDate());

		assertEquals(savedAlert, alert);
	}

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

	@Test
	void testUiControllerAddAttribute() {
		AlertRepository mockRepo = mock(AlertRepository.class);
		UiController controller = new UiController(mockRepo);

		List<Alert> alerts = List.of(new Alert(UUID.randomUUID(), -10.0, -45.0, LocalDateTime.now()));
		when(mockRepo.findTop100ByOrderByDateDesc()).thenReturn(alerts);

		Model mockModel = mock(Model.class);

		String viewName = controller.home(mockModel);

		verify(mockModel).addAttribute("alerts", alerts);
		assertEquals("alerts", viewName);
	}
}
