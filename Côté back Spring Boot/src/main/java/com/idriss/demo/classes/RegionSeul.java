package com.idriss.demo.classes;

public class RegionSeul {//L'objectif de cette classe, c'est qu'on ne va pas récupérer les objets qui sont liées à chaque objet.
	private int codReg;
	private String libReg;
	public RegionSeul() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RegionSeul(int codReg, String libReg) {
		super();
		this.codReg = codReg;
		this.libReg = libReg;
	}
	public int getCodReg() {
		return codReg;
	}
	public void setCodReg(int codReg) {
		this.codReg = codReg;
	}
	public String getLibReg() {
		return libReg;
	}
	public void setLibReg(String libReg) {
		this.libReg = libReg;
	}
	@Override
	public String toString() {
		return "RegionSeul [codReg=" + codReg + ", libReg=" + libReg + "]";
	}
	
	
}
