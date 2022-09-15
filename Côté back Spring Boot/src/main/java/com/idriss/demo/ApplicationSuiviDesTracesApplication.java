package com.idriss.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import com.idriss.demo.service.TraceService;

@SpringBootApplication
@RestController
public class ApplicationSuiviDesTracesApplication {
	
	@Autowired
	TraceService traceService;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApplicationSuiviDesTracesApplication.class, args);
	}
}
