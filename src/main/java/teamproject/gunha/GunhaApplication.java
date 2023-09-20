package teamproject.gunha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GunhaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GunhaApplication.class, args);
	}

}
