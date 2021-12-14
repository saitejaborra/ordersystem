package com.techcrunchers.capstoneprojectdbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
//@EnableAutoConfiguration
public class CapstoneProjectDbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapstoneProjectDbsApplication.class, args);
	}

}
