package org.iqstaffing.assessment;

import org.iqstaffing.assessment.components.Indexer;
import org.iqstaffing.assessment.configurations.ApplicationConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import({Indexer.class, ApplicationConfiguration.class})
class IqStaffingFavouriteRecipesApplicationTests {

	@Test
	void contextLoads() {
	}


	@Test
	public void testMain() {
		// given
		String[] args = new String[] {};

		// when
		IqStaffingFavouriteRecipesApplication.main(args);

		// then
		// nothing
	}
}
