package com.idriss.demo.service;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idriss.demo.classes.AjoutTrace;
import com.idriss.demo.classes.Chemin;
import com.idriss.demo.entities.Agence;
import com.idriss.demo.entities.GAB;
import com.idriss.demo.entities.Region;
import com.idriss.demo.entities.Trace;
import com.idriss.demo.repository.AgenceRepository;
import com.idriss.demo.repository.GABRepository;
import com.idriss.demo.repository.RegionRepository;
import com.idriss.demo.repository.TraceRepository;
import com.idriss.demo.service.TraceServiceImpl.CopieException;

@Service
public class TraceServiceImpl implements TraceService {

	@Autowired
	private TraceRepository traceRepository;

	@Autowired
	private GABRepository gabRepository;
	
	

	public static int executerCommande(String command) throws IOException, InterruptedException {
		String[] commande = {"cmd.exe","/c", command};
		System.out.println("Exécution de la commande:\n");
		ProcessBuilder pb = new ProcessBuilder(commande);
		pb.redirectError(Redirect.INHERIT);
		pb.redirectOutput(Redirect.INHERIT);
		Process p = pb.start();
		System.out.println("En attente2:\n");
		return p.waitFor();
	}
	public static String cheminGabCentral(String cheminGAB) {
		String cheminCentral = "";
		if(cheminGAB.charAt(cheminGAB.length() - 1) == 'l')
			cheminCentral += "NCRM\\";
		else
			cheminCentral += "DN\\";
		
		StringBuffer cheminB = new StringBuffer(cheminGAB);
		cheminB.delete(0, 2);
		for(int i = 0; i<cheminB.length(); i++) {
			if(cheminB.charAt(i) == '\\'){
				cheminB.delete(i, cheminB.length());
				break;
			}
		}
		cheminCentral += cheminB;
		return cheminCentral;
	}
	
	
	@Override
	public Chemin genererCheminTrace(int idTrace) {
		List<GAB> GABs = gabRepository.findAll();
		for(GAB gab : GABs) {
			List<Trace> traces = gab.getTraces();
			for(Trace trace : traces) {
				if(trace.getIdTrace() == idTrace) {
					Chemin chemin = new Chemin();
					String cheminLocalCentral = "assets\\";
					cheminLocalCentral += cheminGabCentral(gab.getUrlGAB()) + "\\" + trace.getNomFichier() + ".txt";
					chemin.setChemin(cheminLocalCentral);
					return chemin;
				}
			}
		}
		return null;
	}

	@Override
	public Trace ajouterTrace(AjoutTrace ajout) throws CopieException {
		String date = ajout.getDateTrace();
		StringBuffer dateB = new StringBuffer(date);
		for(int i = 0; i<dateB.length(); i++) {
			if(dateB.charAt(i) == '-')
				dateB.setCharAt(i, '_');
		}
		date = dateB.toString();
		
		String codeGAB = ajout.getCodeGAB();
		String cheminGAB = gabRepository.findById(codeGAB).get().getUrlGAB();
		String cheminTrace = cheminGAB + "\\EJ_" + date + ".txt";
		String commande = "xcopy " + cheminTrace + " \"C:\\Application-suivi-des-traces-des-guichts-bancaires\\Côté front Angular\\src\\assets\\" + cheminGabCentral(cheminGAB) + "\"";
		System.out.println(commande);
		try {
			int statutCommande = executerCommande(commande);
			if(statutCommande == 0) {
				Date dateD = new Date();
				LocalDate localDate = LocalDate.of(dateD.getYear() + 1900, dateD.getMonth() + 1, dateD.getDate());
				String dateRemontee = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
				Trace trace = new Trace("EJ_" + date, gabRepository.findById(codeGAB).get(), ajout.getDateTrace(), dateRemontee);
				traceRepository.save(trace);
				return trace;
			}
			else {
				throw new CopieException("Le fichier que vous êtes en train de rechercher n'existe pas");
			}

		}
		catch(Exception e) {
			throw new CopieException("C'est une exception");
		}
	}
	
	public class CopieException extends Exception {
	    public CopieException(String message) {
	        super (message);
	    }
	}

	
}
