package com.itsmine.itsmine;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.itsmine.itsmine")
public class ItsmineApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItsmineApplication.class, args);

    }

}



