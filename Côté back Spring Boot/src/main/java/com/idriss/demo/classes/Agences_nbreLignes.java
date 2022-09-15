package com.idriss.demo.classes;

import java.util.List;

public class Agences_nbreLignes {
	List<AgenceRegion> agences;
	int nombreLignes;
	public Agences_nbreLignes() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Agences_nbreLignes(List<AgenceRegion> agences, int nombreLignes) {
		super();
		this.agences = agences;
		this.nombreLignes = nombreLignes;
	}
	public List<AgenceRegion> getAgences() {
		return agences;
	}
	public void setAgences(List<AgenceRegion> agences) {
		this.agences = agences;
	}
	public int getNombreLignes() {
		return nombreLignes;
	}
	public void setNombreLignes(int nombreLignes) {
		this.nombreLignes = nombreLignes;
	}

}
