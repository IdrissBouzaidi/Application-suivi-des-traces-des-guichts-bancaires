package com.idriss.demo.classes;

import java.sql.Date;

public class TraceGab {
	private int idTrace;
	private String nomFichier;
	private Date dateTrx;
	private Date dateRemontee;
	private String cheminTrace;
	private String codeGAB;
	private String libGAB;
	public TraceGab() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TraceGab(int idTrace, String nomFichier, Date dateTrx, Date dateRemontee, String cheminTrace, String codeGAB,
			String libGAB) {
		super();
		this.idTrace = idTrace;
		this.nomFichier = nomFichier;
		this.dateTrx = dateTrx;
		this.dateRemontee = dateRemontee;
		this.cheminTrace = cheminTrace;
		this.codeGAB = codeGAB;
		this.libGAB = libGAB;
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
	public Date getDateTrx() {
		return dateTrx;
	}
	public void setDateTrx(Date dateTrx) {
		this.dateTrx = dateTrx;
	}
	public Date getDateRemontee() {
		return dateRemontee;
	}
	public void setDateRemontee(Date dateRemontee) {
		this.dateRemontee = dateRemontee;
	}
	public String getCheminTrace() {
		return cheminTrace;
	}
	public void setCheminTrace(String cheminTrace) {
		this.cheminTrace = cheminTrace;
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
	@Override
	public String toString() {
		return "GabTrace [idTrace=" + idTrace + ", nomFichier=" + nomFichier + ", dateTrx=" + dateTrx
				+ ", dateRemontee=" + dateRemontee + ", codeGAB=" + codeGAB + ", libGAB=" + libGAB + "]";
	}
	
}
