package com.dnv.impresion.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class PedidoImpresion implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Usuario usuario;
		
	private String nombreArchivoImpresion;
	
	private String nombreArchivoClave;
	
	private boolean color;
	
	private boolean dobleFax;
	
	private int cantidadHojas;
	
	private int cantidadCopias;
	
	private String disenio;
	
	private String tamanioPapel;
	
	private int ubicacionFisica;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JoinColumn(name="pedido_impresion_id")
	private List<PedidoImpresionEstado> pedidoImpresionEstadoList;
		
	public PedidoImpresion(){
		this.fecha = new Date();
		pedidoImpresionEstadoList = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreArchivoImpresion() {
		return nombreArchivoImpresion;
	}

	public void setNombreArchivoImpresion(String nombreArchivoImpresion) {
		this.nombreArchivoImpresion = nombreArchivoImpresion;
	}

	public boolean isColor() {
		return color;
	}

	public void setColor(boolean color) {
		this.color = color;
	}

	public boolean isDobleFax() {
		return dobleFax;
	}

	public void setDobleFax(boolean dobleFax) {
		this.dobleFax = dobleFax;
	}

	public int getCantidadHojas() {
		return cantidadHojas;
	}

	public void setCantidadHojas(int cantidadHojas) {
		this.cantidadHojas = cantidadHojas;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getCantidadCopias() {
		return cantidadCopias;
	}

	public void setCantidadCopias(int cantidadCopias) {
		this.cantidadCopias = cantidadCopias;
	}

	public String getNombreArchivoClave() {
		return nombreArchivoClave;
	}

	public void setNombreArchivoClave(String nombreArchivoClave) {
		this.nombreArchivoClave = nombreArchivoClave;
	}

	public String getDisenio() {
		return disenio;
	}

	public void setDisenio(String disenio) {
		this.disenio = disenio;
	}

	public String getTamanioPapel() {
		return tamanioPapel;
	}

	public void setTamanioPapel(String tamanioPapel) {
		this.tamanioPapel = tamanioPapel;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getUbicacionFisica() {
		return ubicacionFisica;
	}

	public void setUbicacionFisica(int ubicacionFisica) {
		this.ubicacionFisica = ubicacionFisica;
	}

	public List<PedidoImpresionEstado> getPedidoImpresionEstadoList() {
		return pedidoImpresionEstadoList;
	}

	public void setPedidoImpresionEstadoList(List<PedidoImpresionEstado> pedidoImpresionEstadoList) {
		this.pedidoImpresionEstadoList = pedidoImpresionEstadoList;
	}
	
	public void addPedidoImpresionEstado(PedidoImpresionEstado pedidoImpresionEstado){
		this.pedidoImpresionEstadoList.add(pedidoImpresionEstado);
	}



	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
