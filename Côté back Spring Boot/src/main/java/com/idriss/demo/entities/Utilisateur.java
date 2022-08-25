package com.idriss.demo.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Utilisateur {
	@Id
	private String email;
	private String motDePasse;
	public Utilisateur() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Utilisateur(String email, String motDePasse) {
		super();
		this.email = email;
		this.motDePasse = motDePasse;
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
	@Override
	public String toString() {
		return "Utilisateur [email=" + email + ", motDePasse=" + motDePasse + "]";
	}
}
