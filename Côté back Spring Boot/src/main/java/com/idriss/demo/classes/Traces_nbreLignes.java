package com.idriss.demo.classes;

import java.util.List;

public class Traces_nbreLignes {
	List<TraceGab> traces;
	int nombreLignes;
	public Traces_nbreLignes() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Traces_nbreLignes(List<TraceGab> traces, int nombreLignes) {
		super();
		this.traces = traces;
		this.nombreLignes = nombreLignes;
	}
	public List<TraceGab> getTraces() {
		return traces;
	}
	public void setTraces(List<TraceGab> traces) {
		this.traces = traces;
	}
	public int getNombreLignes() {
		return nombreLignes;
	}
	public void setNombreLignes(int nombreLignes) {
		this.nombreLignes = nombreLignes;
	}
	@Override
	public String toString() {
		return "Traces_nbreLignes [traces=" + traces + ", nombreLignes=" + nombreLignes + "]";
	}

}
