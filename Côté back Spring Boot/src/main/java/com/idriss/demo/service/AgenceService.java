package com.idriss.demo.service;

import java.util.List;

import com.idriss.demo.classes.Agences_nbreLignes;
import com.idriss.demo.entities.Agence;

public interface AgenceService {
	Agences_nbreLignes findAgences(String libRegion);
	List<Agence> findAgenceByCode(String codeBurpo);
	int ajouterAgence(String data);
}
