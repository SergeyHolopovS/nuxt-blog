package com.vueblog;

import org.springframework.boot.SpringApplication;

public class TestVueblogApplication {

	public static void main(String[] args) {
		SpringApplication.from(VueblogApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
