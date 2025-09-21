package monitor.lab.cana_fire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CanaFireApplication {

	public static void main(String[] args) {
		SpringApplication.run(CanaFireApplication.class, args);
	}

}
