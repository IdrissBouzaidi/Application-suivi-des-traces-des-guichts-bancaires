package com.idriss.demo.entities;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TempsRecuperation {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "dateSysteme", columnDefinition = "DATE DEFAULT(CURRENT_DATE)")
	private Date dateSysteme;
	private Time heure;
	private byte statut;
	
	public TempsRecuperation() {
		super();
	}

	public TempsRecuperation(int id, Date dateSysteme, Time heure, byte statut) {
		super();
		this.id = id;
		this.dateSysteme = dateSysteme;
		this.heure = heure;
		this.statut = statut;
	}

	public int getId() {
		return id;
	}

	public Date getDateSysteme() {
		return dateSysteme;
	}

	public Time getHeure() {
		return heure;
	}

	public byte getStatut() {
		return statut;
	}

	@Override
	public String toString() {
		return "TempsRecuperation [id=" + id + ", dateSysteme=" + dateSysteme + ", heure=" + heure + ", statut="
				+ statut + "]";
	}
}
