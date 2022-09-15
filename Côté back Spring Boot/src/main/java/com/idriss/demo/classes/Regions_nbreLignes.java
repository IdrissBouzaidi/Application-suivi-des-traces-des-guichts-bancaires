package com.idriss.demo.classes;

import java.util.List;

public class Regions_nbreLignes {
	List<RegionSeul> regions;
	int nombreLignes;
	public Regions_nbreLignes() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Regions_nbreLignes(List<RegionSeul> regions, int nombreLignes) {
		super();
		this.regions = regions;
		this.nombreLignes = nombreLignes;
	}
	public List<RegionSeul> getRegions() {
		return regions;
	}
	public void setRegions(List<RegionSeul> regions) {
		this.regions = regions;
	}
	public int getNombreLignes() {
		return nombreLignes;
	}
	public void setNombreLignes(int nombreLignes) {
		this.nombreLignes = nombreLignes;
	}
	@Override
	public String toString() {
		return "Regions_nbreLignes [regions=" + regions + ", nombreLignes=" + nombreLignes + "]";
	}
	
}
