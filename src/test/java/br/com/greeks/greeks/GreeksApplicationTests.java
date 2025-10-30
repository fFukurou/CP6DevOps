package br.com.greeks.greeks;

import br.com.greeks.greeks.controller.DeityTypeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GreeksApplicationTests {

	@Autowired
	private DeityTypeController deityTypeController;

	@Test
	void contextLoads() {
		assertThat(deityTypeController).isNotNull();
	}

}
