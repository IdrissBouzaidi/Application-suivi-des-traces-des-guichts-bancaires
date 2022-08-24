package com.idriss.demo.entities;

import java.text.SimpleDateFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Trace {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int idTrace;
	private String nomFichier;
	
	@ManyToOne
	private GAB gab;
	
	private String dateTrx;
	private String dateRemontee;
	
	
	
	public Trace() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Trace(String nomFichier, GAB gab, String dateTrx, String dateRemontee) {
		super();
		this.nomFichier = nomFichier;
		this.gab = gab;
		this.dateTrx = dateTrx;
		this.dateRemontee = dateRemontee;
	}
	public int getIdTrace() {
		return idTrace;
	}
	public void setIdTrace(int idTrace) {
		this.idTrace = idTrace;
	}
	public String getNomFichier() {
		return nomFichier;
	}
	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}
	public String getDateTrx() {
		return dateTrx;
	}
	public void setDateTrx(String dateTrx) {
		this.dateTrx = dateTrx;
	}
	public String getDateRemontee() {
		return dateRemontee;
	}
	public void setDateRemontee(String dateRemontee) {
		this.dateRemontee = dateRemontee;
	}
	@Override
	public String toString() {
		return "Trace [idTrace=" + idTrace + ", nomFichier=" + nomFichier + ", gab=" + gab + ", dateTrx=" + dateTrx
				+ ", dateRemontee=" + dateRemontee + "]";
	}
	

}
