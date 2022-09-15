package com.idriss.demo.classes;

import java.util.List;

import com.idriss.demo.entities.Utilisateur;

public class Utilisateurs_nbreLignes {
	List<Utilisateur> utilisateurs;
	int nombreLignes;
	public Utilisateurs_nbreLignes() {
		super();
	}
	public Utilisateurs_nbreLignes(List<Utilisateur> utilisateurs, int nombreLignes) {
		super();
		this.utilisateurs = utilisateurs;
		this.nombreLignes = nombreLignes;
	}
	public List<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}
	public void setUtilisateurs(List<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}
	public int getNombreLignes() {
		return nombreLignes;
	}
	public void setNombreLignes(int nombreLignes) {
		this.nombreLignes = nombreLignes;
	}
}
