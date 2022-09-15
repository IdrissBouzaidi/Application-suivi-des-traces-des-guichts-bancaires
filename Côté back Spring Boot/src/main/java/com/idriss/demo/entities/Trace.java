package com.idriss.demo.entities;

import java.sql.Date;
import java.text.ParseException;

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
	
	private Date dateTrx;
	private Date dateRemontee;
	
	
	
	public Trace() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Trace(String nomFichier, GAB gab, Date dateTrx, Date dateRemontee) throws ParseException {
		super();
		this.nomFichier = nomFichier;
		this.gab = gab;
		this.dateTrx = dateTrx;
		this.dateRemontee = dateRemontee;
	}
	public int getIdTrace() {
		return idTrace;
	}
	public String getNomFichier() {
		return nomFichier;
	}
	public Date getDateTrx() {
		return dateTrx;
	}
	public Date getDateRemontee() {
		return dateRemontee;
	}
	@Override
	public String toString() {
		return "Trace [idTrace=" + idTrace + ", nomFichier=" + nomFichier + ", gab=" + gab + ", dateTrx=" + dateTrx
				+ ", dateRemontee=" + dateRemontee + "]";
	}
	

}
