package com.idriss.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Email {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique=true)
	private String adresseMail;
	private String password;//Pour l'envoyeur
	private byte statut;//1 pour l'envoyeur, 0 pour les récepteurs
	
	
	public Email() {
		super();
	}
	
	public Email(int id, String adresseMail, String password, byte statut) {
		super();
		this.id = id;
		this.adresseMail = adresseMail;
		this.password = password;
		this.statut = statut;
	}

	public int getId() {
		return id;
	}
	public String getAdresseMail() {
		return adresseMail;
	}
	public String getPassword() {
		return password;
	}
	public byte getStatut() {
		return statut;
	}
	
}
