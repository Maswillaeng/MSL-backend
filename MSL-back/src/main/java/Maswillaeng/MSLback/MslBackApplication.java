package Maswillaeng.MSLback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MslBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MslBackApplication.class, args);
	}
}
