package animealth.animealthbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class AnimealthBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimealthBackendApplication.class, args);
	}

}
