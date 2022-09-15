package com.idriss.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idriss.demo.classes.RegionSeul;
import com.idriss.demo.classes.Regions_nbreLignes;
import com.idriss.demo.classes.Tri;
import com.idriss.demo.entities.Region;
import com.idriss.demo.repository.RegionRepository;

@Service
public class RegionServiceImpl implements RegionService {

	@Autowired
	private RegionRepository regionRepository;
	
	@Override
	public List<Region> findAll() {
		return regionRepository.findAll();
	}

	@Override
	public Regions_nbreLignes findRegions(String data) {
		StringBuffer _data = new StringBuffer(data);
		int lignes = -1;
		int lignesTotales = 0;
		String recherche = null;
		String attributATrier = null;
		int l = 0;
		for(int i = 0; i<_data.length(); i++) {
			if(_data.charAt(i) == '&') {
				if(lignes == -1) {
					lignes = Integer.parseInt(_data.substring(0, i));
					l = i+1;
				}
				else if(recherche == null) {
					recherche = _data.substring(l, i);
					attributATrier = _data.substring(i+1);
				}
			}
		}
		List<Region> regions = regionRepository.findAll();
		List<RegionSeul> touteRegion = new ArrayList<>();
		for(Region region : regions)
			touteRegion.add(new RegionSeul(region.getCodReg(), region.getLibReg()));
		touteRegion = recupererElementsChercheables(touteRegion, recherche);
		lignesTotales = touteRegion.size();
		if(!attributATrier.equals("")) {
			boolean inverser = false;
			if(attributATrier.substring(0, 4).equals("DESC")) {
				inverser = true;
				attributATrier = attributATrier.substring(4);
			}
			String[][] tableauTrie = elementsTries(touteRegion, attributATrier);
			Tri.triFusion(tableauTrie);
			touteRegion = trierElements(touteRegion, tableauTrie);
			if(inverser == true) {
				touteRegion = inverser(touteRegion);
			}
		}
		touteRegion = touteRegion.subList(Math.min(lignes, touteRegion.size()), Math.min(lignes + 100, touteRegion.size()));
		return new Regions_nbreLignes(touteRegion, lignesTotales);
	}
	

	public static List<RegionSeul> inverser(List<RegionSeul> listeElements){
		int nbreLignes = listeElements.size();
		List<RegionSeul> elementsInverses = new ArrayList<>();
		for(int i = 0; i<nbreLignes; i++)
			elementsInverses.add(listeElements.get(nbreLignes - i - 1));
		return elementsInverses;
	}
	
	public static String[][] elementsTries(List<RegionSeul> regions, String attribut){
		int nbreLignes = regions.size();
		String[][] tableauTrie = new String[nbreLignes][2];
		for(int i = 0; i<nbreLignes; i++) {
			Integer _i = i;
			tableauTrie[i][0] = _i.toString();
			if(attribut.equals("codReg")) {
				Integer codReg = regions.get(i).getCodReg();
				tableauTrie[i][1] = codReg.toString();
			}
			if(attribut.equals("libReg"))
				tableauTrie[i][1] = regions.get(i).getLibReg();
		}
		return tableauTrie;
	}
	
	public static List<RegionSeul> trierElements(List<RegionSeul> agences, String[][] tableauTrie){
		int nbreLignes = agences.size();
		ArrayList<RegionSeul> regionsTries = new ArrayList<>();
		for(int i = 0; i<nbreLignes; i++)//Pour remplir la liste par 'nbreLignes' valeur qu'on va changer par la suite.
			regionsTries.add(null);
		for(int i = 0; i<nbreLignes; i++) {
			int index = Integer.parseInt(tableauTrie[i][0]);
			regionsTries.set(i, agences.get(index));
		}
		return regionsTries;
	}
	
	static List<RegionSeul> recupererElementsChercheables(List<RegionSeul> regions, String nomRecherche) {
		List<RegionSeul> regionsRecherchees = new ArrayList<>();
		for(RegionSeul region: regions) {
			Integer codReg = region.getCodReg();
			if(codReg.toString().contains(nomRecherche))
				regionsRecherchees.add(region);
			else if(region.getLibReg().contains(nomRecherche))
				regionsRecherchees.add(region);
		}
		return regionsRecherchees;
	}

	@Override
	public int ajouterRegion(String data) {
		String codReg = null;
		String libReg = null;
		StringBuffer dataS = new StringBuffer(data);
		String etatModification = null;
		int position = 0;
		for(int i = 0; i<data.length(); i++) {
			if(dataS.charAt(i) == '&') {
				if(codReg == null) {
					codReg = dataS.substring(position, i);
					position = i+1;
				}
				else {
					libReg = dataS.substring(position, i);
					etatModification = dataS.substring(i+1);
				}
			}
		}
		List<Region> regions = regionRepository.findAll();
		if(etatModification.equals("false")) {
			for(int i = 0; i<regions.size(); i++) {
				if(regions.get(i).getCodReg() == Integer.parseInt(codReg))
					return AgenceServiceImpl.CODE_BURPO_PAS_UNIQUE;
			}
		}
		Region region;
		try {
			region = regionRepository.findById(Integer.parseInt(codReg)).get();
			region.modifier(libReg);
		}
		catch(NoSuchElementException e) {
			region = new Region(Integer.parseInt(codReg), libReg);
		}
		regionRepository.save(region);
		return AgenceServiceImpl.OK;
	}


}
