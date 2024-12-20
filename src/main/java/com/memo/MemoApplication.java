package com.memo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@EnableScheduling
@SpringBootApplication
public class MemoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MemoApplication.class, args);
	}

}
