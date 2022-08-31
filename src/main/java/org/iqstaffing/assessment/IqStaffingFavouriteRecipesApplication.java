package org.iqstaffing.assessment;

import org.iqstaffing.assessment.components.Indexer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IqStaffingFavouriteRecipesApplication {

	public static void main(String[] args) {
		SpringApplication.run(IqStaffingFavouriteRecipesApplication.class, args);
	}

	@Bean
	public ApplicationRunner buildIndex(Indexer indexer) throws Exception {
				return (ApplicationArguments args) -> {
			indexer.indexPersistedData("org.iqstaffing.assessment.models.Recipe");
		};
	}
}
