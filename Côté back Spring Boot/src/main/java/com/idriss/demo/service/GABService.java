package com.idriss.demo.service;

import java.util.List;

import com.idriss.demo.classes.GABs_nbreLignes;
import com.idriss.demo.entities.GAB;


public interface GABService {
	List<GAB> findGabByCode(String codeGAB);
	GABs_nbreLignes findGABs(String data);
	int ajouterGAB(String data);
}
