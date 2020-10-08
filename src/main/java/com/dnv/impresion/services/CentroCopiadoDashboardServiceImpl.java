package com.dnv.impresion.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dnv.impresion.dto.pedidosImpresion.PedidoImpresionCentroDTO;
import com.dnv.impresion.entity.dao.PedidoImpresionDao;
import com.dnv.impresion.entity.dao.PedidoImpresionEstadoDao;
import com.dnv.impresion.entity.dao.UsuarioDao;
import com.dnv.impresion.models.entity.PedidoImpresion;
import com.dnv.impresion.models.entity.Role;
import com.dnv.impresion.models.entity.Usuario;
import com.dnv.impresion.models.entity.PedidoImpresionEstado;
import com.dnv.impresion.models.entity.PedidoImpresionEstado.EstadoPedidoImpresion;


@Service
public class CentroCopiadoDashboardServiceImpl implements CentroCopiadoDashboardService{

	@Autowired
	PedidoImpresionDao pedidoImpresionDao;
	
	@Autowired
	PedidoImpresionEstadoDao pedidoImpresionEstadoDao;
	
	@Autowired
	UsuarioDao usuarioDao;

	public List<PedidoImpresionCentroDTO> obtenerPedidosImpresion(String tipoEstado){

		List<PedidoImpresion> pedidoImpresionList = pedidoImpresionDao.findAll();
		
		List<PedidoImpresion> pedidoImpresionAMostrarList = new ArrayList<>();
		
		List<PedidoImpresionCentroDTO> pedidoImpresionCentroDtoList = new ArrayList<>();
		
		switch(tipoEstado) {
			case "Intermedio":
				
				for(PedidoImpresion o: pedidoImpresionList){
					for(PedidoImpresionEstado o2: o.getPedidoImpresionEstadoList()) {
						if(o2.getFechaFin() == null) {
							
							if(o2.getEstadoPedidoImpresion().equals(com.dnv.impresion.models.entity.PedidoImpresionEstado.EstadoPedidoImpresion.SIN_ASIGNAR) ||
								o2.getEstadoPedidoImpresion().equals(com.dnv.impresion.models.entity.PedidoImpresionEstado.EstadoPedidoImpresion.PARA_IMPRIMIR ) ||
								o2.getEstadoPedidoImpresion().equals(com.dnv.impresion.models.entity.PedidoImpresionEstado.EstadoPedidoImpresion.PARA_RETIRAR)) {
								
								pedidoImpresionAMostrarList.add(o);
								break;
							}
						}
					}
				}		
				break;
				
			case "Finalizado":
				
				for(PedidoImpresion o: pedidoImpresionList){
					for(PedidoImpresionEstado o2: o.getPedidoImpresionEstadoList()) {
						if(o2.getFechaFin() == null) {
							
							if(o2.getEstadoPedidoImpresion().equals(com.dnv.impresion.models.entity.PedidoImpresionEstado.EstadoPedidoImpresion.CANCELADO)||
								o2.getEstadoPedidoImpresion().equals(com.dnv.impresion.models.entity.PedidoImpresionEstado.EstadoPedidoImpresion.RECHAZADO ) ||
								o2.getEstadoPedidoImpresion().equals(com.dnv.impresion.models.entity.PedidoImpresionEstado.EstadoPedidoImpresion.RETIRADO)) {
								
								pedidoImpresionAMostrarList.add(o);
								break;
							}
						}
					}
				}		
				break;
				
		}
		
		for(PedidoImpresion o : pedidoImpresionAMostrarList) {
						
			String fullNameAgente = o.getUsuario().getApellido() + ", " + o.getUsuario().getNombre();
			
			PedidoImpresionEstado pedidoImpresionEstado = null;
			
			for(PedidoImpresionEstado o2: o.getPedidoImpresionEstadoList()) {
				if(o2.getFechaFin() == null) {
					pedidoImpresionEstado = o2;
					break;
				}
			}
			
			PedidoImpresionCentroDTO pedidoImpresionCentroDTO 
					= new PedidoImpresionCentroDTO(
							o.getId(),
							o.getUsuario().getPrioridad(),
							o.getFecha(),
							fullNameAgente,
							o.getUsuario().getCuit(),
							o.getNombreArchivoImpresion(),
							pedidoImpresionEstado.getEstadoPedidoImpresion().toString(),
							o.getCantidadCopias(),
							o.getCantidadHojas(),
							o.isDobleFax(),
							o.isColor(),
							o.getTamanioPapel(),
							o.getDisenio());
			
			pedidoImpresionCentroDtoList.add(pedidoImpresionCentroDTO);
		
		}
		
		return pedidoImpresionCentroDtoList;
		
	}
	
	// Mejorar Excepciones
	public void asignarPedidoImpresion(String username, Long idPedidoImpresion) throws Exception {
		
		System.out.println(username);
		
		Usuario usuarioEncargado = usuarioDao.findByUsername(username);

		Optional<PedidoImpresion> pedidoImpresion = pedidoImpresionDao.findById(idPedidoImpresion);
		
		if (pedidoImpresion.isPresent()) {

			boolean esCentro = false;

			for (Role o : usuarioEncargado.getRoleList()) {
				if (o.getNombre().equals("ROLE_CCYD")) {
					esCentro = true;
					
					PedidoImpresionEstado pedidoImpresionEstadoNuevo = new PedidoImpresionEstado();
					pedidoImpresionEstadoNuevo.setUsuario(usuarioEncargado);
					pedidoImpresionEstadoNuevo.setEstadoPedidoImpresion(EstadoPedidoImpresion.PARA_IMPRIMIR);
					
					PedidoImpresionEstado pedidoImpresionEstado = null;
					
					for(PedidoImpresionEstado o2: pedidoImpresion.get().getPedidoImpresionEstadoList()) {
						if(o2.getFechaFin() == null) {
							pedidoImpresionEstado = o2;
							break;
						}
					}
					
					pedidoImpresionEstado.setFechaFin(new Date());
					pedidoImpresion.get().addPedidoImpresionEstado(pedidoImpresionEstadoNuevo);
					
					//Save
					pedidoImpresionEstadoDao.save(pedidoImpresionEstado);
					pedidoImpresionEstadoDao.save(pedidoImpresionEstadoNuevo);
					pedidoImpresionDao.save(pedidoImpresion.get());
					
					break;
				}
			}
		
			if(!esCentro) {
				throw new Exception("El usuario no tiene permisos para asignar pedidos de impresion.");
			}	

		} else {
			throw new Exception("No existe el pedido seleccionado.");
		}

	}

	//Rechazar Pedido
	public void rechazarPedidoImpresion(String username, Long idPedidoImpresion) {
		
		Usuario usuarioEncargado = usuarioDao.findByUsername(username);
		
		Optional<PedidoImpresion> pedidoImpresion = pedidoImpresionDao.findById(idPedidoImpresion);
		
		if(pedidoImpresion.isPresent()) {
			
			PedidoImpresionEstado pedidoImpresionEstadoNuevo = new PedidoImpresionEstado();
			pedidoImpresionEstadoNuevo.setUsuario(usuarioEncargado);
			pedidoImpresionEstadoNuevo.setEstadoPedidoImpresion(EstadoPedidoImpresion.RECHAZADO);
			
			PedidoImpresionEstado pedidoImpresionEstado = null;
			
			for(PedidoImpresionEstado o: pedidoImpresion.get().getPedidoImpresionEstadoList()) {
				if(o.getFechaFin() == null) {
					pedidoImpresionEstado = o;
					break;
				}
			}
			
			pedidoImpresionEstado.setFechaFin(new Date());
			
			pedidoImpresion.get().addPedidoImpresionEstado(pedidoImpresionEstadoNuevo);
			
			//Save
			pedidoImpresionEstadoDao.save(pedidoImpresionEstado);
			pedidoImpresionEstadoDao.save(pedidoImpresionEstadoNuevo);
			pedidoImpresionDao.save(pedidoImpresion.get());
			
		}else {
			throw new NullPointerException("No se ha encontrado el pedido de impresion.");
		}
	}
	
	//ConfirmarPedido
	public void confirmarPedido(String username, Long idPedidoImpresion) {
		
		Usuario usuarioEncargado = usuarioDao.findByUsername(username);
		
		Optional<PedidoImpresion> pedidoImpresion = pedidoImpresionDao.findById(idPedidoImpresion);
		
		if(pedidoImpresion.isPresent()) {
			
			PedidoImpresionEstado pedidoImpresionEstadoNuevo = new PedidoImpresionEstado();
			pedidoImpresionEstadoNuevo.setUsuario(usuarioEncargado);
			pedidoImpresionEstadoNuevo.setEstadoPedidoImpresion(EstadoPedidoImpresion.PARA_RETIRAR);
			
			PedidoImpresionEstado pedidoImpresionEstado = null;
			
			for(PedidoImpresionEstado o: pedidoImpresion.get().getPedidoImpresionEstadoList()) {
				if(o.getFechaFin() == null) {
					pedidoImpresionEstado = o;
					break;
				}
			}
			
			pedidoImpresionEstado.setFechaFin(new Date());
			
			pedidoImpresion.get().addPedidoImpresionEstado(pedidoImpresionEstadoNuevo);
			
			//Save
			pedidoImpresionEstadoDao.save(pedidoImpresionEstado);
			pedidoImpresionEstadoDao.save(pedidoImpresionEstadoNuevo);
			pedidoImpresionDao.save(pedidoImpresion.get());
			
		}else {
			throw new NullPointerException("No se ha encontrado el pedido de impresion.");
		}
		
	}
	
	//EntregarPedido
	public void entregarPedidoImpresion(String username, Long idPedidoImpresion) {
		
		Usuario usuarioEncargado = usuarioDao.findByUsername(username);
		
		Optional<PedidoImpresion> pedidoImpresion = pedidoImpresionDao.findById(idPedidoImpresion);
		
		if(pedidoImpresion.isPresent()) {
		
			PedidoImpresionEstado pedidoImpresionEstadoNuevo = new PedidoImpresionEstado();
			pedidoImpresionEstadoNuevo.setUsuario(usuarioEncargado);
			pedidoImpresionEstadoNuevo.setEstadoPedidoImpresion(EstadoPedidoImpresion.RETIRADO);
			
			PedidoImpresionEstado pedidoImpresionEstado = null;
			
			for(PedidoImpresionEstado o: pedidoImpresion.get().getPedidoImpresionEstadoList()) {
				if(o.getFechaFin() == null) {
					pedidoImpresionEstado = o;
					break;
				}
			}
			
			pedidoImpresionEstado.setFechaFin(new Date());
			
			pedidoImpresion.get().addPedidoImpresionEstado(pedidoImpresionEstadoNuevo);
			
			//Save
			pedidoImpresionEstadoDao.save(pedidoImpresionEstado);
			pedidoImpresionEstadoDao.save(pedidoImpresionEstadoNuevo);
			pedidoImpresionDao.save(pedidoImpresion.get());
		
		}else {
			throw new NullPointerException("No se ha encontrado el pedido de impresion.");
		}
		
	}

}
