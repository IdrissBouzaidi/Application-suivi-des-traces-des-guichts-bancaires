package com.idriss.demo.classes;

import java.util.List;

public class GABs_nbreLignes {
	List<GabAgence> GABs;
	int nombreLignes;
	public GABs_nbreLignes() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GABs_nbreLignes(List<GabAgence> gABs, int nombreLignes) {
		super();
		GABs = gABs;
		this.nombreLignes = nombreLignes;
	}
	public List<GabAgence> getGABs() {
		return GABs;
	}
	public void setGABs(List<GabAgence> gABs) {
		GABs = gABs;
	}
	public int getNombreLignes() {
		return nombreLignes;
	}
	public void setNombreLignes(int nombreLignes) {
		this.nombreLignes = nombreLignes;
	}
	@Override
	public String toString() {
		return "GABs_nbreLignes [GABs=" + GABs + ", nombreLignes=" + nombreLignes + "]";
	}

}
