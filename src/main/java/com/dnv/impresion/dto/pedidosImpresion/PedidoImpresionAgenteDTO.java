package com.dnv.impresion.dto.pedidosImpresion;

import java.util.Date;

public class PedidoImpresionAgenteDTO {
	
	private Long idPedidoImpresion;
	private Date fechaPedidoImpresion;
	private String nombreArchivo;
	private String estadoPedidoImpresion;
	private int cantidadCopias;
	private int cantidadHojas;
	private boolean dobleFaz;
	private boolean color;
	private String tamanioPapel;
	private String disenio;
	private String feedbackPedido;
	
	public PedidoImpresionAgenteDTO(Long idPedidoImpresion, Date fechaPedidoImpresion, String nombreArchivo,
			String estadoPedidoImpresion, int cantidadCopias, int cantidadHojas,
			String tamanioPapel, boolean dobleFaz, boolean color, String disenio, String feedbackPedido) {
		
		this.idPedidoImpresion = idPedidoImpresion;
		this.fechaPedidoImpresion = fechaPedidoImpresion;
		this.nombreArchivo = nombreArchivo;
		this.estadoPedidoImpresion = estadoPedidoImpresion;
		this.cantidadCopias = cantidadCopias;
		this.cantidadHojas = cantidadHojas;
		this.dobleFaz = dobleFaz;
		this.color = color;
		this.tamanioPapel = tamanioPapel;
		this.disenio = disenio;
		this.feedbackPedido = feedbackPedido;
	}

	public Long getIdPedidoImpresion() {
		return idPedidoImpresion;
	}
	public void setIdPedidoImpresion(Long idPedidoImpresion) {
		this.idPedidoImpresion = idPedidoImpresion;
	}
	public Date getFechaPedidoImpresion() {
		return fechaPedidoImpresion;
	}
	public void setFechaPedidoImpresion(Date fechaPedidoImpresion) {
		this.fechaPedidoImpresion = fechaPedidoImpresion;
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

	public String getFeedbackPedido() {
		return feedbackPedido;
	}

	public void setFeedbackPedido(String feedbackPedido) {
		this.feedbackPedido = feedbackPedido;
	}
	
	
	
	

}
