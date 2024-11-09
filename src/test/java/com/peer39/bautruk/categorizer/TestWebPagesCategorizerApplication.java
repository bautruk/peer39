package com.peer39.bautruk.categorizer;

import org.springframework.boot.SpringApplication;

public class TestWebPagesCategorizerApplication {

	public static void main(String[] args) {
		SpringApplication.from(WebPagesCategorizerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
