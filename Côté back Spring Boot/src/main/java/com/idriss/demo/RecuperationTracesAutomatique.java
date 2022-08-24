package com.idriss.demo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.idriss.demo.classes.AjoutTrace;
import com.idriss.demo.entities.GAB;
import com.idriss.demo.entities.Trace;
import com.idriss.demo.repository.GABRepository;
import com.idriss.demo.service.GABService;
import com.idriss.demo.service.TraceService;

@Component
public class RecuperationTracesAutomatique implements CommandLineRunner {

	@Autowired
	private GABRepository gabRepository;
	
	@Autowired
	TraceService traceService;

    @Override
    public void run(String...args) throws Exception {
		while(true) {
			Date dateActuelle = new Date();
			dateActuelle.setSeconds(0);
			Date date = new Date(dateActuelle.getYear(), dateActuelle.getMonth(), dateActuelle.getDate(), 16, 37);
			if(date.toString().equals(dateActuelle.toString())) {
				LocalDate localDate = LocalDate.of(date.getYear() + 1900, date.getMonth() + 1, date.getDate() - 1);
				String dateTrx = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
				List<GAB> GABs = gabRepository.findAll();
				for(GAB gab : GABs) {
					Trace trace = traceService.ajouterTrace(new AjoutTrace(gab.getCodeGAB(), dateTrx));
				}
				Thread.sleep(1000 * 60 * 60 * 24 - 1000l);
			}
			else {
				System.out.println(date.getTime());
				long tempsRestant = date.getTime() - new Date().getTime() - 10l;;
				if(date.getTime()<new Date().getTime())
					tempsRestant += 24 * 60 * 60 * 1000l;
				System.out.println(tempsRestant);
				if(tempsRestant > 0)
					Thread.sleep(tempsRestant);
			}
		}
    }
}