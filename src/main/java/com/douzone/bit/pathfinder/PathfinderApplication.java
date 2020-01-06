package com.douzone.bit.pathfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PathfinderApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(PathfinderApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PathfinderApplication.class);
	}

}
