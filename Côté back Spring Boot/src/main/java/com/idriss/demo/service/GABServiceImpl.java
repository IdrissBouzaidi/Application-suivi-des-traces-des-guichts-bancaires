package com.idriss.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idriss.demo.classes.GABs_nbreLignes;
import com.idriss.demo.classes.GabAgence;
import com.idriss.demo.classes.Tri;
import com.idriss.demo.entities.Agence;
import com.idriss.demo.entities.GAB;
import com.idriss.demo.methods.Methods;
import com.idriss.demo.repository.AgenceRepository;
import com.idriss.demo.repository.GABRepository;

@Service
public class GABServiceImpl implements GABService {

	@Autowired
	private GABRepository gabRepository;
	
	@Autowired
	private AgenceRepository agenceRepository;

	@Override
	public List<GAB> findGabByCode(String codeGAB) {
		List<GAB> GABs = new ArrayList<>();
		if(codeGAB.equals(""))
			GABs = gabRepository.findAll();
		else
			GABs.add(gabRepository.findById(codeGAB).get());
		return GABs;
	}

	@Override
	public GABs_nbreLignes findGABs(String data) {
		StringBuffer _data = new StringBuffer(data);
		String codeBurpo = null;
		int lignes = -1;
		int lignesTotales = 0;
		String recherche = null;
		String attributATrier = null;
		int l = 0;//On va l'utiliser comme position du premier lettre de recherche dans la chaine de caractère qui est dans le paramêtre
		for(int i = 0; i<_data.length(); i++) {
			if(_data.charAt(i) == '&') {
				if(codeBurpo == null) {
					codeBurpo = _data.substring(0, i);
					l = i+1;
				}
				else if(lignes == -1) {
					lignes = Integer.parseInt(_data.substring(l, i));
					l = i+1;
				}
				else if(recherche == null) {
					recherche = _data.substring(l, i);
					attributATrier = _data.substring(i+1);
				}
			}
		}
		List<Agence> agences = agenceRepository.findAll();
		List<GabAgence> toutGAB = new ArrayList<>();
		if(codeBurpo.equals("")) {
			for(Agence agence : agences) {
				List<GAB> GABs = agence.getGABs();
				for(GAB gab : GABs)
					toutGAB.add(new GabAgence(gab.getCodeGAB(), gab.getLibGAB(), gab.getUrlGAB(), gab.getUrlGABArchive(), gab.getDateDernierRemontee(), agence.getCodeBurpo(), agence.getLibelleBurpo()));
			}
		}
		else {
			for(Agence agence : agences) {
				if(agence.getCodeBurpo().equals(codeBurpo)) {
					List<GAB> GABs = agence.getGABs();
					for(GAB gab : GABs)
						toutGAB.add(new GabAgence(gab.getCodeGAB(), gab.getLibGAB(), gab.getUrlGAB(), gab.getUrlGABArchive(), gab.getDateDernierRemontee(), agence.getCodeBurpo(), agence.getLibelleBurpo()));
				}
			}
		}
		toutGAB = recupererElementsChercheables(toutGAB, recherche);
		lignesTotales = toutGAB.size();
		if(!attributATrier.equals("")) {
			boolean inverser = false;
			if(attributATrier.substring(0, 4).equals("DESC")) {
				inverser = true;
				attributATrier = attributATrier.substring(4);
			}
			String[][] tableauTrie = elementsTries(toutGAB, attributATrier);
			Tri.triFusion(tableauTrie);
			toutGAB = trierElements(toutGAB, tableauTrie);
			if(inverser == true) {
				toutGAB = inverser(toutGAB);
			}
		}
		toutGAB = toutGAB.subList(Math.min(lignes, toutGAB.size()), Math.min(lignes + 100, toutGAB.size()));
		return new GABs_nbreLignes(toutGAB, lignesTotales);
	}
	
	public static List<GabAgence> inverser(List<GabAgence> listeElements){
		int nbreLignes = listeElements.size();
		List<GabAgence> elementsInverses = new ArrayList<>();
		for(int i = 0; i<nbreLignes; i++)
			elementsInverses.add(listeElements.get(nbreLignes - i - 1));
		return elementsInverses;
	}

	public static String[][] elementsTries(List<GabAgence> GABs, String attribut){
		int nbreLignes = GABs.size();
		String[][] tableauTrie = new String[nbreLignes][2];
		for(int i = 0; i<nbreLignes; i++) {
			Integer _i = i;
			tableauTrie[i][0] = _i.toString();
			if(attribut.equals("codeGAB")) {
				tableauTrie[i][1] = GABs.get(i).getCodeGAB();
			}
			if(attribut.equals("libGAB")) {
				tableauTrie[i][1] = GABs.get(i).getLibGAB();
			}
			if(attribut.equals("urlGAB")) {
				tableauTrie[i][1] = GABs.get(i).getUrlGAB();
			}
			if(attribut.equals("urlGABArchive")) {
				tableauTrie[i][1] = GABs.get(i).getUrlGABArchive();
			}
			if(attribut.equals("dateDernierRemontee")) {
				tableauTrie[i][1] = Methods.parseDateString(GABs.get(i).getDateDernierRemontee());
			}
			if(attribut.equals("libelleBurpo")) {
				tableauTrie[i][1] = GABs.get(i).getLibelleBurpo();
			}
		}
		return tableauTrie;
	}
	
	public static List<GabAgence> trierElements(List<GabAgence> GABs, String[][] tableauTrie){
		int nbreLignes = GABs.size();
		ArrayList<GabAgence> GABsTries = new ArrayList<>();
		for(int i = 0; i<nbreLignes; i++) {//Pour remplir la liste par 'nbreLignes' valeur qu'on va changer par la suite.
			GABsTries.add(null);
		}
		for(int i = 0; i<nbreLignes; i++) {
			int index = Integer.parseInt(tableauTrie[i][0]);
			GABsTries.set(i, GABs.get(index));
		}
		return GABsTries;
	}
	
	static List<GabAgence> recupererElementsChercheables(List<GabAgence> GABs, String nomRecherche) {
		List<GabAgence> GABsRecherches = new ArrayList<>();
		for(GabAgence gab: GABs) {
			if(gab.getCodeGAB().contains(nomRecherche))
				GABsRecherches.add(gab);
			else if(gab.getLibGAB().contains(nomRecherche))
				GABsRecherches.add(gab);
			else if(gab.getUrlGAB().contains(nomRecherche))
				GABsRecherches.add(gab);
			else if(gab.getUrlGABArchive() != null && gab.getUrlGABArchive().contains(nomRecherche))
				GABsRecherches.add(gab);
			else if(gab.getDateDernierRemontee() != null && Methods.parseDateString((gab.getDateDernierRemontee())).contains(nomRecherche))
				GABsRecherches.add(gab);
			else if(gab.getCodeBurpo().contains(nomRecherche))
				GABsRecherches.add(gab);
			else if(gab.getLibelleBurpo().contains(nomRecherche))
				GABsRecherches.add(gab);
		}
		return GABsRecherches;
	}

	@Override
	public int ajouterGAB(String data) {
		String codeGAB = null;
		String libGAB = null;
		String urlGAB = null;
		String urlGABArchive = null;
		Agence agence = null;
		String etatModification = null;
		
		StringBuffer dataS = new StringBuffer(data);
		int position = 0;
		for(int i = 0; i<data.length(); i++) {
			if(dataS.charAt(i) == '&') {
				if(codeGAB == null) {
					codeGAB = dataS.substring(position, i);
					position = i+1;
				}
				else if(libGAB == null) {
					libGAB = dataS.substring(position, i);
					position = i+1;
				}
				else if(urlGAB == null) {
					urlGAB = dataS.substring(position, i);
					position = i+1;
				}
				else if(urlGABArchive == null) {
					urlGABArchive = dataS.substring(position, i);
					position = i+1;
				}
				else if(agence == null) {
					String codeBurpo = dataS.substring(position, i);
					agence = agenceRepository.findById(codeBurpo).get();
					etatModification = dataS.substring(i+1);
				}
			}
		}
		List<GAB> GABs = gabRepository.findAll();
		if(etatModification.equals("false")) {
			for(int i = 0; i<GABs.size(); i++) {
				if(GABs.get(i).getCodeGAB().equals(codeGAB)) {
					return AgenceServiceImpl.CODE_BURPO_PAS_UNIQUE;
				}
			}
		}
		/**
		 * Pour les URLs, les '\' ne doivent pas s'envoyer de la même façon, sinon ça va générer des problèmes
		 * La solution est qu'on remplace les '\' par 'µ' chez le client pour pouvoir accéder à cette méthode (sinon on ne pourra pas y accéder)
		 * et ici on remplace à nouveau pour les stoquer dans la BDD correctement
		 */
		StringBuffer urlGAB_b = new StringBuffer(urlGAB);
		StringBuffer urlGABArchive_b = new StringBuffer(urlGABArchive);
		for(int i = 0; i<urlGAB_b.length(); i++) {
			if(urlGAB_b.charAt(i) == 'µ')
				urlGAB_b.setCharAt(i, '\\');
		}
		urlGAB = urlGAB_b.toString();
		for(int i = 0; i<urlGABArchive_b.length(); i++) {
			if(urlGABArchive_b.charAt(i) == 'µ')
				urlGABArchive_b.setCharAt(i, '\\');
		}
		urlGABArchive = urlGABArchive_b.toString();
		GAB gab;
		try {
			gab = gabRepository.findById(codeGAB).get();
			gab.modifier(libGAB, agence, urlGAB, urlGABArchive);
		}
		catch(NoSuchElementException e) {
			gab = new GAB(codeGAB, libGAB, agence, urlGAB, urlGABArchive);
		}
		gabRepository.save(gab);
		return AgenceServiceImpl.OK;
	}
}
