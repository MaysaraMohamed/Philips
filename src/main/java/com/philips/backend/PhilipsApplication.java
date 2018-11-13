package com.philips.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-08-20
 */

@SpringBootApplication
@EnableScheduling
public class PhilipsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhilipsApplication.class, args);
	}

}