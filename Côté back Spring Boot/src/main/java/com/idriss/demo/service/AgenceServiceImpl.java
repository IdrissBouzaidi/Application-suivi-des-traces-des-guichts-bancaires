package com.idriss.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idriss.demo.classes.AgenceRegion;
import com.idriss.demo.classes.Agences_nbreLignes;
import com.idriss.demo.classes.Tri;
import com.idriss.demo.entities.Agence;
import com.idriss.demo.entities.Region;
import com.idriss.demo.repository.AgenceRepository;
import com.idriss.demo.repository.RegionRepository;

@Service
public class AgenceServiceImpl implements AgenceService {
	final static int OK = 0;
	final static int CODE_BURPO_PAS_UNIQUE = 1;

	@Autowired
	private AgenceRepository agenceRepository;
	
	@Autowired
	private RegionRepository regionRepository;
	
	@Override
	public List<Agence> findAgenceByCode(String codeBurpo) {
		List<Agence> agences = new ArrayList<>();
		if(codeBurpo.equals("")) 
			agences = agenceRepository.findAll();
		else
			agences.add(agenceRepository.findById(codeBurpo).get());
		return agences;
	}

	@Override
	public Agences_nbreLignes findAgences(String  data) {
		StringBuffer _data = new StringBuffer(data);
		String codReg = null;
		int lignes = -1;
		int lignesTotales = 0;
		String recherche = null;
		String attributATrier = null;
		int l = 0;
		for(int i = 0; i<_data.length(); i++) {
			if(_data.charAt(i) == '&') {
				if(codReg == null) {
					codReg = _data.substring(0, i);
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
		List<Region> regions = regionRepository.findAll();
		List<AgenceRegion> touteAgence = new ArrayList<>();
		if(codReg.equals("")) {
			for(Region region : regions) {
				List<Agence> agences = region.getAgences();
				for(Agence agence : agences)
					touteAgence.add(new AgenceRegion(agence, region.getCodReg(), region.getLibReg()));
			}
		}
		else {
			for(Region region : regions) {
				if(region.getCodReg() == Integer.parseInt(codReg)) {
					List<Agence> agences = region.getAgences();
					for(Agence agence : agences)
						touteAgence.add(new AgenceRegion(agence, region.getCodReg(), region.getLibReg()));
				}
			}
		}
		touteAgence = recupererElementsChercheables(touteAgence, recherche);
		lignesTotales = touteAgence.size();
		if(!attributATrier.equals("")) {
			boolean inverser = false;
			if(attributATrier.substring(0, 4).equals("DESC")) {
				inverser = true;
				attributATrier = attributATrier.substring(4);
			}
			String[][] tableauTrie = elementsTries(touteAgence, attributATrier);
			Tri.triFusion(tableauTrie);
			touteAgence = trierElements(touteAgence, tableauTrie);
			if(inverser == true) {
				touteAgence = inverser(touteAgence);
			}
		}
		touteAgence = touteAgence.subList(Math.min(lignes, touteAgence.size()), Math.min(lignes + 100, touteAgence.size()));
		return new Agences_nbreLignes(touteAgence, lignesTotales);
	}

	public static List<AgenceRegion> inverser(List<AgenceRegion> listeElements){
		int nbreLignes = listeElements.size();
		List<AgenceRegion> elementsInverses = new ArrayList<>();
		for(int i = 0; i<nbreLignes; i++)
			elementsInverses.add(listeElements.get(nbreLignes - i - 1));
		return elementsInverses;
	}
	
	public static String[][] elementsTries(List<AgenceRegion> agences, String attribut){
		int nbreLignes = agences.size();
		String[][] tableauTrie = new String[nbreLignes][2];
		for(int i = 0; i<nbreLignes; i++) {
			Integer _i = i;
			tableauTrie[i][0] = _i.toString();
			if(attribut.equals("codeBurpo")) {
				tableauTrie[i][1] = agences.get(i).getCodeBurpo();
			}
			if(attribut.equals("libelleBurpo")) {
				tableauTrie[i][1] = agences.get(i).getLibelleBurpo();
			}
			if(attribut.equals("libReg")) {
				tableauTrie[i][1] = agences.get(i).getLibReg();
			}
		}
		return tableauTrie;
	}
	
	public static List<AgenceRegion> trierElements(List<AgenceRegion> agences, String[][] tableauTrie){
		int nbreLignes = agences.size();
		ArrayList<AgenceRegion> agencesTries = new ArrayList<>();
		for(int i = 0; i<nbreLignes; i++) {//Pour remplir la liste par 'nbreLignes' valeur qu'on va changer par la suite.
			agencesTries.add(null);
		}
		for(int i = 0; i<nbreLignes; i++) {
			int index = Integer.parseInt(tableauTrie[i][0]);
			agencesTries.set(i, agences.get(index));
		}
		return agencesTries;
	}
	
	
	static List<AgenceRegion> recupererElementsChercheables(List<AgenceRegion> agences, String nomRecherche) {
		List<AgenceRegion> agencesRecherchees = new ArrayList<>();
		for(AgenceRegion agence: agences) {
			if(agence.getCodeBurpo().contains(nomRecherche))
				agencesRecherchees.add(agence);
			else if(agence.getLibelleBurpo().contains(nomRecherche))
				agencesRecherchees.add(agence);
			else if(agence.getLibReg().contains(nomRecherche))
				agencesRecherchees.add(agence);
		}
		return agencesRecherchees;
	}

	@Override
	public int ajouterAgence(String data) {
		String codeBurpo = null;
		String libelleBurpo = null;
		Region region = null;
		String etatModification = null;
		StringBuffer dataS = new StringBuffer(data);
		int position = 0;
		for(int i = 0; i<data.length(); i++) {
			if(dataS.charAt(i) == '&') {
				if(codeBurpo == null) {
					codeBurpo = dataS.substring(position, i);
					position = i+1;
				}
				else if(libelleBurpo == null ) {
					libelleBurpo = dataS.substring(position, i);
					position = i+1;
				}
				else if(region == null) {
					int codReg = Integer.parseInt(dataS.substring(position, i));
					region = regionRepository.findById(codReg).get();
					etatModification = dataS.substring(i+1);
				}
			}
		}
		List<Agence> agences = agenceRepository.findAll();
		if(etatModification.equals("false")) {
			for(int i = 0; i<agences.size(); i++) {
				if(agences.get(i).getCodeBurpo().equals(codeBurpo)) {
					return CODE_BURPO_PAS_UNIQUE;
				}
			}
		}
		Agence agence;
		try {
			agence = agenceRepository.findById(codeBurpo).get();
			agence.modifier(libelleBurpo, region);
		}
		catch(NoSuchElementException e) {
			agence = new Agence(codeBurpo, libelleBurpo, region);
		}
		agenceRepository.save(agence);
		return OK;
	}

	

}
