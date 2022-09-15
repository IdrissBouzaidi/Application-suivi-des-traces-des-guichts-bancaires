package com.idriss.demo;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.idriss.demo.entities.GAB;
import com.idriss.demo.entities.TempsRecuperation;
import com.idriss.demo.methods.Methods;
import com.idriss.demo.repository.GABRepository;
import com.idriss.demo.repository.TempsRecuperationRepository;
import com.idriss.demo.service.EnvoieMail;
import com.idriss.demo.service.EnvoieMailImpl;
import com.idriss.demo.service.TraceService;

import Exceptions.DoubleHeureRecupereeException;

@Component
public class RecuperationTracesAutomatique implements CommandLineRunner {

	@Autowired
	private GABRepository gabRepository;
	
	@Autowired
	private TempsRecuperationRepository heureRecuperationRepository;
	
	@Autowired
	TraceService traceService;
	
	@Autowired
	EnvoieMail envoieMail;
    
    @Override
    public void run(String...args) throws MessagingException, InterruptedException, ParseException, IOException {
		try {
			while(true) {
				Date dateActuelle = new Date();
				dateActuelle.setSeconds(0);
				Time heureRecuperation = recupererHeureExecution();
				@SuppressWarnings("deprecation")
				Date date = new Date(dateActuelle.getYear(), dateActuelle.getMonth(), dateActuelle.getDate(), heureRecuperation.getHours(), heureRecuperation.getMinutes(), 0);
				if(date.toString().equals(dateActuelle.toString())) {
					System.out.println("C'est le temps pour rcupérer les traces!!");
					Date dateHier = new Date(date.getTime() - 1000*60*60*24);
					String dateTrx = Methods.parseDateString(dateHier);
					List<GAB> GABs = gabRepository.findAll();
					EnvoieMailImpl.resultatGABs = null;
					EnvoieMailImpl.resultatGABs = new String[GABs.size()][2];
					for(int i = 0; i<GABs.size(); i++) {
						GAB gab = GABs.get(i);
						String[][] resultatTraces = traceService.recupererTraces(gab.getCodeGAB() + "&" + dateTrx + "&" + dateTrx, TraceService.RECUOERATION_TRACE_AUTOMATIQUE);
						EnvoieMailImpl.resultatGABs[i] = new String[]{resultatTraces[0][0], gab.getLibGAB()};
					}
					envoieMail.envoyerMail();
					Thread.sleep(1000 * 60);
				}
				else {
					long periode = 0;// = date.getTime() - new Date().getTime() - 10l;
					periode = 2 * 1000l;
					Thread.sleep(periode);
				}
			}
		}
		catch(DoubleHeureRecupereeException e) {
			System.out.println("Exception : " + e.getMessage());
		}
	}
    public Time recupererHeureExecution() throws DoubleHeureRecupereeException {
    	List<TempsRecuperation> heuresRecuperation = heureRecuperationRepository.findAll();
    	int nbreHeuresRecuperees = 0;
    	Time heureRecuperee = null;
    	for(TempsRecuperation heure: heuresRecuperation) {
    		if(heure.getStatut() == 1) {
    			heureRecuperee = heure.getHeure();
    			nbreHeuresRecuperees++;
    		}
    	}
    	if(nbreHeuresRecuperees > 1 || nbreHeuresRecuperees == 0)
    		throw new DoubleHeureRecupereeException(nbreHeuresRecuperees + " heures trouvées avec le statut '1'");
    	else 
    		return heureRecuperee;
    }

}