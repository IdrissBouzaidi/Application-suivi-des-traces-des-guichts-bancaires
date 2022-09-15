package com.idriss.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idriss.demo.classes.TraceGab;
import com.idriss.demo.classes.Traces_nbreLignes;
import com.idriss.demo.classes.Tri;
import com.idriss.demo.entities.GAB;
import com.idriss.demo.entities.Trace;
import com.idriss.demo.methods.Methods;
import com.idriss.demo.repository.GABRepository;
import com.idriss.demo.repository.TraceRepository;


@Service
public class TraceServiceImpl implements TraceService {

	private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
	static String attributATrier = null;
	
	@Autowired
	private TraceRepository traceRepository;

	@Autowired
	private GABRepository gabRepository;
	
	@Autowired
	GABService gabService;
	

	private static boolean procDone(Process p) {
		try {
			p.exitValue();
			return true;
		} catch (IllegalThreadStateException e) {
			return false;
		}
	}
	
	/**
	 * La liste des fichiers qu'on va supprimer dans l'archive
	 * @param date
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static List<String> getListeFichiersDansArchive(String urlArchive, int nbreJours) throws IOException, ParseException{
		String dateActuelleS = Methods.getDateActuelle();
		Date dateActuelle = new SimpleDateFormat("yyyy-MM-dd").parse(dateActuelleS);
		long dateMinMilliSec = dateActuelle.getTime() - 1000 * 60 * 60 * 24l * nbreJours;
		Date dateMinimumD = new Date(dateMinMilliSec);
		String dateMinimum = Methods.parseDateString(dateMinimumD);
		dateMinimum = urlArchive + "/EJ_" + dateMinimum + ".txt";
		StringBuffer cmdRecuperation = new StringBuffer("dir " + urlArchive + "\\* /b");
		for(int i = 0; i<cmdRecuperation.length(); i++) {
			if(cmdRecuperation.charAt(i) == '\\')
				cmdRecuperation.setCharAt(i, '/');
		}
		StringBuffer dateMinimumB = new StringBuffer(dateMinimum);
		for(int i = 0; i<dateMinimum.length(); i++) {
			if(dateMinimumB.charAt(i) == '\\')
				dateMinimumB.setCharAt(i, '/');
			if(dateMinimumB.charAt(i) == '-')
				dateMinimumB.setCharAt(i, '_');
		}
		dateMinimum = dateMinimumB.toString();
		Process p = Runtime.getRuntime().exec(cmdRecuperation.toString());
		// read the standard output of the command
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String trace;
		List<String> commandesFichiers = new ArrayList<>();
		while (!procDone(p)) {
			while ((trace = stdInput.readLine()) != null) {
				if(trace.compareTo(dateMinimum) < 0) {
					String commande = "del " + trace;
					commandesFichiers.add(commande);
				}
			}
		}
		
		stdInput.close();
		return commandesFichiers;
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
	public Traces_nbreLignes findTraces(String data) {
		StringBuffer _data = new StringBuffer(data);
		String codeGAB = null;
		int lignes = -1;
		int lignesTotales = 0;
		String recherche = null;
		int l = 0;//On va l'utiliser comme position du premier lettre de recherche dans la chaine de caractère qui est dans le paramêtre
		for(int i = 0; i<_data.length(); i++) {
			if(_data.charAt(i) == '&') {
				if(l == 0) {
					codeGAB = _data.substring(0, i);
					l = i+1;
				}
				else if(lignes == -1) {
					lignes = Integer.parseInt(_data.substring(l, i));
					l = i+1;
				}
				else {
					recherche = _data.substring(l, i);
					attributATrier = _data.substring(i+1);
				}
			}
		}
		List<GAB> GABs = gabRepository.findAll();
		List<TraceGab> touteTrace = new ArrayList<>();
		if(codeGAB.equals("")) {
			for(GAB gab : GABs) {
				List<Trace> traces = gab.getTraces();
				for(Trace trace : traces) {
					String cheminLocalCentral = "assets\\";
					cheminLocalCentral += cheminGabCentral(gab.getUrlGAB()) + "\\" + trace.getNomFichier() + ".txt";
					touteTrace.add(new TraceGab(trace.getIdTrace(), trace.getNomFichier(), trace.getDateTrx(), trace.getDateRemontee(), cheminLocalCentral, gab.getCodeGAB(), gab.getLibGAB()));
				}
			}
		}
		else {
			for(GAB gab : GABs) {
				if(gab.getCodeGAB().equals(codeGAB)) {
					List<Trace> traces = gab.getTraces();
					for(Trace trace : traces) {
						String cheminLocalCentral = "assets\\";
						cheminLocalCentral += cheminGabCentral(gab.getUrlGAB()) + "\\" + trace.getNomFichier() + ".txt";
						touteTrace.add(new TraceGab(trace.getIdTrace(), trace.getNomFichier(), trace.getDateTrx(), trace.getDateRemontee(), cheminLocalCentral, gab.getCodeGAB(), gab.getLibGAB()));
					}
				}
			}
		}
		touteTrace = recupererElementsChercheables(touteTrace, recherche);
		lignesTotales = touteTrace.size();
		if(!attributATrier.equals("")) {
			boolean inverser = false;
			if(attributATrier.substring(0, 4).equals("DESC")) {
				inverser = true;
				attributATrier = attributATrier.substring(4);
			}
			String[][] tableauTrie = elementsTries(touteTrace, attributATrier);
			Tri.triFusion(tableauTrie);
			touteTrace = trierElements(touteTrace, tableauTrie);
			if(inverser == true) {
				touteTrace = inverser(touteTrace);
			}
		}
		touteTrace = touteTrace.subList(Math.min(lignes, touteTrace.size()), Math.min(lignes + 100, touteTrace.size()));
		return new Traces_nbreLignes(touteTrace, lignesTotales);
	}

	public static List<TraceGab> inverser(List<TraceGab> listeElements){
		int nbreLignes = listeElements.size();
		List<TraceGab> elementsInverses = new ArrayList<>();
		for(int i = 0; i<nbreLignes; i++)
			elementsInverses.add(listeElements.get(nbreLignes - i - 1));
		return elementsInverses;
	}

	public static String[][] elementsTries(List<TraceGab> traces, String attribut){
		int nbreLignes = traces.size();
		String[][] tableauTrie = new String[nbreLignes][2];
		for(int i = 0; i<nbreLignes; i++) {
			Integer _i = i;
			tableauTrie[i][0] = _i.toString();
			if(attribut.equals("idTrace")) {
				Integer idTrace = traces.get(i).getIdTrace();
				tableauTrie[i][1] = idTrace.toString();
			}
			if(attribut.equals("nomFichier")) 
				tableauTrie[i][1] = traces.get(i).getNomFichier();
			if(attribut.equals("dateTrx"))
				tableauTrie[i][1] = Methods.parseDateString((traces.get(i).getDateTrx()));
			if(attribut.equals("dateRemontee"))
				tableauTrie[i][1] = Methods.parseDateString((traces.get(i).getDateRemontee()));
			if(attribut.equals("libGAB"))
				tableauTrie[i][1] = traces.get(i).getLibGAB();
		}
		return tableauTrie;
	}
	
	public static List<TraceGab> trierElements(List<TraceGab> traces, String[][] tableauTrie){
		int nbreLignes = traces.size();
		ArrayList<TraceGab> tracesTries = new ArrayList<>();
		for(int i = 0; i<nbreLignes; i++)//Pour remplir la liste par 'nbreLignes' valeur qu'on va changer par la suite.
			tracesTries.add(null);
		for(int i = 0; i<nbreLignes; i++) {
			int index = Integer.parseInt(tableauTrie[i][0]);
			tracesTries.set(i, traces.get(index));
		}
		return tracesTries;
	}
	
	static List<TraceGab> recupererElementsChercheables(List<TraceGab> traces, String nomRecherche) {
		int longueur = nomRecherche.length();
		String dateDebut = null;
		String dateFin = null;
		for(int i = 0; i<longueur; i++) {
			if(nomRecherche.charAt(0) == '{' && nomRecherche.charAt(longueur-1) == '}') {
				for(int j = 1; j<longueur-1; j++) {
					if(nomRecherche.charAt(j) == ' ') {
						dateDebut = nomRecherche.substring(1, j);
						dateFin = nomRecherche.substring(j+1, longueur - 1);
					}
				}
			}
		}
		List<TraceGab> tracesRecherchees = new ArrayList<>();
		for(TraceGab trace: traces) {
			Integer idTrace = trace.getIdTrace();
			if(dateDebut != null) {
				if((dateDebut.compareTo(Methods.parseDateString(trace.getDateTrx())) <= 0 || dateDebut.equals("")) && (dateFin.compareTo(Methods.parseDateString(trace.getDateTrx())) >= 0 || dateFin.equals(""))) {
					tracesRecherchees.add(trace);
					if(attributATrier.equals(""))
						attributATrier = "dateTrx";
				}
			}
			else if(idTrace.toString().contains(nomRecherche))
				tracesRecherchees.add(trace);
			else if(trace.getNomFichier().contains(nomRecherche))
				tracesRecherchees.add(trace);
			else if(Methods.parseDateString(trace.getDateTrx()).contains(nomRecherche))
				tracesRecherchees.add(trace);
			else if(Methods.parseDateString(trace.getDateRemontee()).contains(nomRecherche))
				tracesRecherchees.add(trace);
			else if(trace.getCodeGAB().contains(nomRecherche))
				tracesRecherchees.add(trace);
			else if(trace.getLibGAB().contains(nomRecherche))
				tracesRecherchees.add(trace);
		}
		return tracesRecherchees;
	}

	@Override
	public String[][] recupererTraces(String data, int statut) throws ParseException, IOException, InterruptedException {//statut = 0 : la méthode est appelée pour récupérer la trace d'hier de manière automatique, statut = 1 : La méthode est appelée à partir du client (Angular)
		String codeGAB = null;
		String dateDebut = null;
		String dateFin = null;
		List<String> dates = new ArrayList<String>();
		int l = 0;
		for(int i = 0; i<data.length(); i++) {
			if(data.charAt(i) == '&') {
				if(codeGAB == null) {
					codeGAB = data.substring(0, i);
					l = i+1;
				}
				else if(dateDebut == null) {
					dateDebut = data.substring(l, i);
					dateFin = data.substring(i+1);
				}
			}
		}
		Date dateD = new SimpleDateFormat("yyyy-MM-dd").parse(dateDebut);
		Date dateF = new SimpleDateFormat("yyyy-MM-dd").parse(dateFin);
		Date date = dateD;
		if(dateDebut.equals(dateFin))
			dates.add(dateDebut);
		GAB gab = gabRepository.findById(codeGAB).get();
		if(!dateDebut.equals(dateFin) && dateF.after(dateD)) {
			dates.add(dateDebut);
			do {
				date = new Date(date.getTime() + MILLIS_IN_A_DAY);
				String dateTrx = Methods.parseDateString(date);
				dates.add(dateTrx);
			}
			while(!date.equals(dateF));
		}
		String cheminUrlGAB = gab.getUrlGAB();
		String urlGABArchive = gab.getUrlGABArchive();
		List<String> commandes1 = new ArrayList<>();
		List<String> commandes2 = new ArrayList<>();
		List<String> commandes3 = new ArrayList<>();
		for(String d : dates) {
			StringBuffer dateB = new StringBuffer(d);
			for(int j = 0; j<dateB.length(); j++) {
				if(dateB.charAt(j) == '-')
					dateB.setCharAt(j, '_');
			}
			d = dateB.toString();
			String commande = "robocopy " + cheminUrlGAB + " \"C:\\Application-suivi-des-traces-des-guichts-bancaires\\Côté front Angular\\src\\assets\\" + cheminGabCentral(cheminUrlGAB) + "\" " + "EJ_" + d + ".txt /XO /r:2 /w:1 /NFL /NDL /NJH /NJS /nc /ns /np";
			commandes1.add(commande);
			commande = "robocopy " + cheminUrlGAB + " " + urlGABArchive + " EJ_" + d + ".txt /XO /r:2 /w:1 /NFL /NDL /NJH /NJS /nc /ns /np";
			commandes2.add(commande);
			commande = "del " + cheminUrlGAB + "\\EJ_" + d + ".txt";
			commandes3.add(commande);
		}
		String[][] sortie;
		if(statut == RECUOERATION_TRACE_VIA_CLIENT)
			sortie =  new String[2][dates.size()];
		else//Récupération des traces automatiquement
			sortie = new String[2][1];
		for(int i = 0; i<dates.size(); i++) {
			System.out.println("Pour la trace " + dates.get(i));
			String commande1 = commandes1.get(i);
			String commande2 = commandes2.get(i);
			String commande3 = commandes3.get(i);
			
			int statutCommande = Methods.executerCommande(commande1);
			Integer _statutCommande = statutCommande;
			sortie[0][i] = _statutCommande.toString();
			sortie[1][i] = dates.get(i);
			switch(statutCommande) {
				case 1:
					Methods.executerCommande(commande2);
					Methods.executerCommande(commande3);
					String dateRemontee = Methods.getDateActuelle();
					gab.setDateDernierRemontee(dateRemontee);
					gabRepository.save(gab);
					Trace trace = new Trace("EJ_" + dates.get(i), gab, Methods.parseStringToDate(dates.get(i)), Methods.parseStringToDate(dateRemontee));
					traceRepository.save(trace);
					System.out.println("Le fichier a été bien ctransféré");
					break;
				case 0:
					System.out.println("Il existe déjà un fichier qui a le même nom");
					break;
				case 2:
					System.out.println("Le fichier spécifié n'a pas été trouvé");
					break;
				case 16:
					System.out.println("On n'a pas pu accéder au chemin réseau (erreur d'accés au GAB)");
					break;
			}
			if(statutCommande != 16 && statut == TraceService.RECUOERATION_TRACE_AUTOMATIQUE) {
				List<String> commandes4 = getListeFichiersDansArchive(urlGABArchive, 30);//Les commandes pour supprimer les fichiers les plus récents de l'archive
				for(String commande : commandes4) {
					commande = Methods.changerCaracteres(commande, new char[] {'/'}, new char[] {'\\'});
					Methods.executerCommande(commande);
				}
			}
		}
		return sortie;
	}
}
