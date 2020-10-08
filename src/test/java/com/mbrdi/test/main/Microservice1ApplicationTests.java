package com.mbrdi.test.main;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.*")
@SpringBootTest
class Microservice1ApplicationTests {

	@Test
	void contextLoads() {
	}

}
