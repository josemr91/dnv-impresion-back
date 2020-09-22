package com.dnv.impresion.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class PedidoImpresionEstado implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaInicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFin;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Usuario usuario;
	
	@Enumerated(value = EnumType.STRING)
	private EstadoPedidoImpresion estadoPedidoImpresion;
	
	public enum EstadoPedidoImpresion{
		SIN_ASIGNAR,
		PARA_IMPRIMIR,
		CANCELADO,
		RECHAZADO,
		PARA_RETIRAR,
		RETIRADO,
		FINALIZADO
	}
	
	public PedidoImpresionEstado() {
		this.fechaInicio = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public EstadoPedidoImpresion getEstadoPedidoImpresion() {
		return estadoPedidoImpresion;
	}

	public void setEstadoPedidoImpresion(EstadoPedidoImpresion estadoPedidoImpresion) {
		this.estadoPedidoImpresion = estadoPedidoImpresion;
	}
	

}
