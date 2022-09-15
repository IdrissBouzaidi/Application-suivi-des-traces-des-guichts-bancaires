package com.idriss.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Utilisateur {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int codeU;
	private String nom;
	private String prenom;
	@Column(unique=true)
	private String email;
	private String motDePasse;
	private int profil;
	public Utilisateur() {
		super();
	}
	public Utilisateur(int profil) {
		super();
		this.profil = profil;
	}
	public Utilisateur(String nom, String prenom, String email, String motDePasse, int profil) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.motDePasse = motDePasse;
		this.profil = profil;
	}
	public int getCodeU() {
		return codeU;
	}
	public void setCodeU(int codeU) {
		this.codeU = codeU;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	public int getProfil() {
		return profil;
	}
	public void setProfil(int profil) {
		this.profil = profil;
	}
	public void modifier(String nom, String prenom, String email, String motDePasse, int profil) {
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.motDePasse = motDePasse;
		this.profil = profil;
	}
	
}
