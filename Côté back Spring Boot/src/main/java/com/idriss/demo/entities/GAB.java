package com.idriss.demo.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	public void setTraces(List<Trace> traces) {
		this.traces = traces;
	}
	public GAB() {
		super();
	}
	public GAB(String codeGAB, String libGAB, Agence agence, String urlGAB, String urlGABArchive,
			Date dateDernierRemontee) {
		super();
		this.codeGAB = codeGAB;
		this.libGAB = libGAB;
		this.agence = agence;
		this.urlGAB = urlGAB;
		this.urlGABArchive = urlGABArchive;
		this.dateDernierRemontee = dateDernierRemontee;
	}
	public String getCodeGAB() {
		return codeGAB;
	}
	public void setCodeGAB(String codeGAB) {
		this.codeGAB = codeGAB;
	}
	public String getLibGAB() {
		return libGAB;
	}
	public void setLibGAB(String libGAB) {
		this.libGAB = libGAB;
	}
	public String getUrlGAB() {
		return urlGAB;
	}
	public void setUrlGAB(String urlGAB) {
		this.urlGAB = urlGAB;
	}
	public String getUrlGABArchive() {
		return urlGABArchive;
	}
	public void setUrlGABArchive(String urlGABArchive) {
		this.urlGABArchive = urlGABArchive;
	}
	public Date getDateDernierRemontee() {
		return dateDernierRemontee;
	}
	public void setDateDernierRemontee(Date dateDernierRemontee) {
		this.dateDernierRemontee = dateDernierRemontee;
	}
	@Override
	public String toString() {
		return "GAB [codeGAB=" + codeGAB + ", libGAB=" + libGAB + ", agence=" + agence + ", urlGAB=" + urlGAB
				+ ", urlGABArchive=" + urlGABArchive + ", dateDernierRemontee=" + dateDernierRemontee + "]";
	}
	

}
