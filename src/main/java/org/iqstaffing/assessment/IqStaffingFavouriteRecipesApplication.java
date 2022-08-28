package org.iqstaffing.assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IqStaffingFavouriteRecipesApplication {

	public static void main(String[] args) {
		SpringApplication.run(IqStaffingFavouriteRecipesApplication.class, args);
	}

}
