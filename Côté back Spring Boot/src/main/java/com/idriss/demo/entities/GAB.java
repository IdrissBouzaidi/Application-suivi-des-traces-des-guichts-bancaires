package com.idriss.demo.entities;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.idriss.demo.methods.Methods;

@Entity
public class GAB {
	@Id
	private String codeGAB;
	private String libGAB;

	@OneToMany (mappedBy = "gab")
	private List<Trace> traces;
	
	@ManyToOne
	private Agence agence;
	
	private String urlGAB;
	private String urlGABArchive;
	private Date dateDernierRemontee;
	
	
	public List<Trace> getTraces() {
		return traces;
	}
	public GAB() {
		super();
	}
	public GAB(String codeGAB, String libGAB, Agence agence, String urlGAB, String urlGABArchive) {
		super();
		this.codeGAB = codeGAB;
		this.libGAB = libGAB;
		this.agence = agence;
		this.urlGAB = urlGAB;
		this.urlGABArchive = urlGABArchive;
	}
	public String getCodeGAB() {
		return codeGAB;
	}
	public String getLibGAB() {
		return libGAB;
	}
	public String getUrlGAB() {
		return urlGAB;
	}
	public String getUrlGABArchive() {
		return urlGABArchive;
	}
	public Date getDateDernierRemontee() {
		return dateDernierRemontee;
	}
	public void setDateDernierRemontee(String dateDernierRemontee) throws ParseException {
		this.dateDernierRemontee = Methods.parseStringToDate(dateDernierRemontee);
	}
	@Override
	public String toString() {
		return "GAB [codeGAB=" + codeGAB + ", libGAB=" + libGAB + ", agence=" + agence + ", urlGAB=" + urlGAB
				+ ", urlGABArchive=" + urlGABArchive + ", dateDernierRemontee=" + dateDernierRemontee + "]";
	}
	public void modifier(String libGAB, Agence agence, String urlGAB, String urlGABArchive) {
		this.libGAB = libGAB;
		this.agence = agence;
		this.urlGAB = urlGAB;
		this.urlGABArchive = urlGABArchive;
	}
	

}
