package com.idriss.demo.classes;

import java.sql.Date;
import java.text.ParseException;

import com.idriss.demo.methods.Methods;

public class GabAgence {
	private String codeGAB;
	private String libGAB;
	private String urlGAB;
	private String urlGABArchive;
	private Date dateDernierRemontee;
	private String codeBurpo;
	private String libelleBurpo;
	public GabAgence() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GabAgence(String codeGAB, String libGAB, String urlGAB, String urlGABArchive, Date dateDernierRemontee,
			String codeBurpo, String libelleBurpo) {
		super();
		this.codeGAB = codeGAB;
		this.libGAB = libGAB;
		this.urlGAB = urlGAB;
		this.urlGABArchive = urlGABArchive;
		this.dateDernierRemontee = dateDernierRemontee;
		this.codeBurpo = codeBurpo;
		this.libelleBurpo = libelleBurpo;
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
	public void setDateDernierRemontee(String dateDernierRemontee) throws ParseException {
		this.dateDernierRemontee = Methods.parseStringToDate(dateDernierRemontee);
	}
	public String getCodeBurpo() {
		return codeBurpo;
	}
	public void setCodeBurpo(String codeBurpo) {
		this.codeBurpo = codeBurpo;
	}
	public String getLibelleBurpo() {
		return libelleBurpo;
	}
	public void setLibelleBurpo(String libelleBurpo) {
		this.libelleBurpo = libelleBurpo;
	}
	@Override
	public String toString() {
		return "RegionGab [codeGAB=" + codeGAB + ", libGAB=" + libGAB + ", urlGAB=" + urlGAB + ", urlGABArchive="
				+ urlGABArchive + ", dateDernierRemontee=" + dateDernierRemontee + ", codeBurpo=" + codeBurpo
				+ ", libelleBurpo=" + libelleBurpo + "]";
	}
	
}
