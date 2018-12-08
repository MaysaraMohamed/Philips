package com.philips.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.philips.backend.common.FileStorageProperties;

/**
 * @author maysara.mohamed
 * @version 1.0
 * @since 2018-08-20
 */

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class PhilipsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhilipsApplication.class, args);
	}

}