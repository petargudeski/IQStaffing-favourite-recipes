package org.iqstaffing.assessment.configurations;

import org.iqstaffing.assessment.repositories.SearchRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "org.iqstaffing.assessment.repositories",
        repositoryBaseClass = SearchRepositoryImpl.class)
public class ApplicationConfiguration {
}
