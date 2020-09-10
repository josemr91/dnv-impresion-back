package com.dnv.impresion.dto.pedidosImpresion;

import java.util.Date;

public class PedidoImpresionCentroDTO {
	
	private Long idPedidoImpresion;
	private String prioridad;
	private Date fechaPedidoImpresion;
	private String fullNameAgente;
	private String cuitAgente;
	private String nombreArchivo;
	private String estadoPedidoImpresion;
	private int cantidadCopias;
	private int cantidadHojas;
	private boolean dobleFaz;
	private boolean color;
	private String tamanioPapel;
	private String disenio;
	
	public PedidoImpresionCentroDTO(Long idPedidoImpresion, String prioridad, Date fechaPedidoImpresion,
			String fullNameAgente, String cuitAgente, String nombreArchivo, String estadoPedidoImpresion,
			int cantidadCopias, int cantidadHojas, boolean dobleFaz, boolean color, String tamanioPapel,
			String disenio) {

		this.idPedidoImpresion = idPedidoImpresion;
		this.prioridad = prioridad;
		this.fechaPedidoImpresion = fechaPedidoImpresion;
		this.fullNameAgente = fullNameAgente;
		this.cuitAgente = cuitAgente;
		this.nombreArchivo = nombreArchivo;
		this.estadoPedidoImpresion = estadoPedidoImpresion;
		this.cantidadCopias = cantidadCopias;
		this.cantidadHojas = cantidadHojas;
		this.dobleFaz = dobleFaz;
		this.color = color;
		this.tamanioPapel = tamanioPapel;
		this.disenio = disenio;
	}

	public Long getIdPedidoImpresion() {
		return idPedidoImpresion;
	}

	public void setIdPedidoImpresion(Long idPedidoImpresion) {
		this.idPedidoImpresion = idPedidoImpresion;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public Date getFechaPedidoImpresion() {
		return fechaPedidoImpresion;
	}

	public void setFechaPedidoImpresion(Date fechaPedidoImpresion) {
		this.fechaPedidoImpresion = fechaPedidoImpresion;
	}

	public String getFullNameAgente() {
		return fullNameAgente;
	}

	public void setFullNameAgente(String fullNameAgente) {
		this.fullNameAgente = fullNameAgente;
	}

	public String getCuitAgente() {
		return cuitAgente;
	}

	public void setCuitAgente(String cuitAgente) {
		this.cuitAgente = cuitAgente;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getEstadoPedidoImpresion() {
		return estadoPedidoImpresion;
	}

	public void setEstadoPedidoImpresion(String estadoPedidoImpresion) {
		this.estadoPedidoImpresion = estadoPedidoImpresion;
	}

	public int getCantidadCopias() {
		return cantidadCopias;
	}

	public void setCantidadCopias(int cantidadCopias) {
		this.cantidadCopias = cantidadCopias;
	}

	public int getCantidadHojas() {
		return cantidadHojas;
	}

	public void setCantidadHojas(int cantidadHojas) {
		this.cantidadHojas = cantidadHojas;
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

	public String getTamanioPapel() {
		return tamanioPapel;
	}

	public void setTamanioPapel(String tamanioPapel) {
		this.tamanioPapel = tamanioPapel;
	}

	public String getDisenio() {
		return disenio;
	}

	public void setDisenio(String disenio) {
		this.disenio = disenio;
	}

	
	
	
	

}
