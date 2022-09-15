package com.idriss.demo.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Region {
	@Id
	private int codReg;
	@Column(unique=true)
	private String libReg;
	@OneToMany (mappedBy = "region")
	private List<Agence> agences;
	
	public Region() {
		super();
	}
	public Region(int codReg, String libReg) {
		super();
		this.codReg = codReg;
		this.libReg = libReg;
	}
	public int getCodReg() {
		return codReg;
	}
	public String getLibReg() {
		return libReg;
	}
	public List<Agence> getAgences() {
		return agences;
	}
	public void modifier(String libReg) {
		this.libReg = libReg;
	}

	@Override
	public String toString() {
		return "Region [codReg=" + codReg + ", libReg=" + libReg + "]";
	}
	
}
