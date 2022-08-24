package com.idriss.demo;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.idriss.demo.classes.AjoutTrace;
import com.idriss.demo.controleur.ControleurSuiviDesTraces;
import com.idriss.demo.entities.Trace;
import com.idriss.demo.service.TraceService;
import com.idriss.demo.service.TraceServiceImpl;

@SpringBootApplication
@RestController
public class ApplicationSuiviDesTracesApplication {
	
	@Autowired
	TraceService traceService;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ApplicationSuiviDesTracesApplication.class, args);
	}
}
