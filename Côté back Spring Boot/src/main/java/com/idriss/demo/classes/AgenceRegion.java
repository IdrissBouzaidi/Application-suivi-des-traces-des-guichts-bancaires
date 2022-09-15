package com.idriss.demo.classes;

import com.idriss.demo.entities.Agence;

public class AgenceRegion {
	private String codeBurpo;
	private String libelleBurpo;
	private int codReg;
	private String libReg;
	public AgenceRegion() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AgenceRegion(Agence agence, int codReg, String libReg) {
		super();
		this.codeBurpo = agence.getCodeBurpo();
		this.libelleBurpo = agence.getLibelleBurpo();
		this.codReg = codReg;
		this.libReg = libReg;
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
		return "AgenceRegion [codeBurpo=" + codeBurpo + ", libelleBurpo=" + libelleBurpo + ", codReg=" + codReg
				+ ", libReg=" + libReg + "]";
	}
	
	
}
