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
