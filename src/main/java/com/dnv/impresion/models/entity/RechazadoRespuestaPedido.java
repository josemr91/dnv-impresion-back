package com.dnv.impresion.models.entity;

import javax.persistence.Entity;


public class RechazadoRespuestaPedido extends RespuestaPedido{
	
	private static final long serialVersionUID = 1L;
	
	private MotivoRechazoPedido motivoRechazoPedido;

	public MotivoRechazoPedido getMotivoRechazoPedido() {
		return motivoRechazoPedido;
	}

	public void setMotivoRechazoPedido(MotivoRechazoPedido motivoRechazoPedido) {
		this.motivoRechazoPedido = motivoRechazoPedido;
	}
	
	

}
