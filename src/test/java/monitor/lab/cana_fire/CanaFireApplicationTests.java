package monitor.lab.cana_fire;

import monitor.lab.cana_fire.domain.Alert;
import monitor.lab.cana_fire.domain.Hotspot;
import monitor.lab.cana_fire.repository.AlertRepository;
import monitor.lab.cana_fire.service.AlertService;
import monitor.lab.cana_fire.service.HotspotService;
import monitor.lab.cana_fire.web.UiController;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.ui.Model;
import static org.mockito.Mockito.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CanaFireApplicationTests {

	@Test
	void testAlertServiceCreateAlert() {
		AlertRepository mockRepo = Mockito.mock(AlertRepository.class);
		AlertService service = new AlertService(mockRepo);

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
	void testHotspotServiceHandle() throws Exception {
		AlertService mockAlertService = Mockito.mock(AlertService.class);

		String geoJson = """
            {
              "type": "Polygon",
              "coordinates": [
                [
                  [-50.0, -20.0],
                  [-50.0, -19.0],
                  [-49.0, -19.0],
                  [-49.0, -20.0],
                  [-50.0, -20.0]
                ]
              ]
            }
            """;

		Resource fakeResource = new ByteArrayResource(geoJson.getBytes());

		HotspotService hotspotService = new HotspotService(fakeResource, mockAlertService);

		Hotspot hotspot = new Hotspot();
		hotspot.setLat(-19.5);
		hotspot.setLon(-49.5);

		hotspotService.handle(hotspot);

		Mockito.verify(mockAlertService, Mockito.times(1)).createAlert(hotspot);
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
