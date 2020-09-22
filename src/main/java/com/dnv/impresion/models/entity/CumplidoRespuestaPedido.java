package com.dnv.impresion.models.entity;

import javax.persistence.Entity;


public class CumplidoRespuestaPedido extends RespuestaPedido{
	
	private static final long serialVersionUID = 1L;

	private String tamanioHoja;
	
	private int copias;
	
	private boolean dobleFaz;
	
	private boolean color;

	public String getTamanioHoja() {
		return tamanioHoja;
	}

	public void setTamanioHoja(String tamanioHoja) {
		this.tamanioHoja = tamanioHoja;
	}

	public int getCopias() {
		return copias;
	}

	public void setCopias(int copias) {
		this.copias = copias;
	}

	public boolean isDobleFaz() {
		return dobleFaz;
	}

	public void setDobleFaz(boolean dobleFaz) {
		this.dobleFaz = dobleFaz;
	}

	public boolean isColor() {
		return color;
	}

	public void setColor(boolean color) {
		this.color = color;
	}
	
	

}
