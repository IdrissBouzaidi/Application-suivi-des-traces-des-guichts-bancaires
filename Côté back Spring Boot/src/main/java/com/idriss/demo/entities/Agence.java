package com.idriss.demo.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Agence {
	@Id
	private String codeBurpo;

	private String libelleBurpo;
	
	@OneToMany (mappedBy = "agence")
	private List<GAB> GABs;
	
	@ManyToOne
	private Region region;

	public Agence() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Agence(String codeBurpo, String libelleBurpo, Region region) {
		super();
		this.codeBurpo = codeBurpo;
		this.libelleBurpo = libelleBurpo;
		this.region = region;
	}
	public String getCodeBurpo() {
		return codeBurpo;
	}
	public String getLibelleBurpo() {
		return libelleBurpo;
	}
	public List<GAB> getGABs() {
		return GABs;
	}
	public void modifier(String libelleBurpo, Region region) {
		this.libelleBurpo = libelleBurpo;
		this.region = region;
	}
	

}
