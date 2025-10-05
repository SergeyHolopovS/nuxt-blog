package com.vueblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class VueblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(VueblogApplication.class, args);
	}

}
