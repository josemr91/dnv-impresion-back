package com.dnv.impresion.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dnv.impresion.dto.pedidosImpresion.PedidoImpresionAgenteDTO;
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
	
	public Page<PedidoImpresionCentroDTO> obtenerPedidosImpresion(String tipoEstado, Pageable pageable){
		
		List<PedidoImpresion> pagePedidoImpresion = pedidoImpresionDao.obtenerPedidosIntermedios();
				
		List<PedidoImpresionCentroDTO> pedidoImpresionCentroDtoList = new ArrayList<>();
		
		
		// Convertir a DTO
		
		for(PedidoImpresion o : pagePedidoImpresion) {
						
			pedidoImpresionCentroDtoList.add(this.toPedidoImpresionCentroDTO(o));	
		}
		
		// Convertir List en Page
		 
		int start = (int) pageable.getOffset();
		int end = (start + pageable.getPageSize()) > pedidoImpresionCentroDtoList.size() ? pedidoImpresionCentroDtoList.size() : (start + pageable.getPageSize());
		
		return new PageImpl<PedidoImpresionCentroDTO>(pedidoImpresionCentroDtoList.subList(start, end), pageable, pedidoImpresionCentroDtoList.size());

	}
	
	
	
	// Mejorar Excepciones
	public void asignarPedidoImpresion(String username, Long idPedidoImpresion) throws Exception {
				
		Usuario usuarioEncargado = usuarioDao.findByUsername(username);

		Optional<PedidoImpresion> pedidoImpresion = pedidoImpresionDao.findById(idPedidoImpresion);
		
		if (pedidoImpresion.isPresent()) {

			boolean esCentro = false;

			for (Role o : usuarioEncargado.getRoleList()) {
				if (o.getNombre().equals("ROLE_CCYD")) {
					esCentro = true;
					
					PedidoImpresionEstado pedidoImpresionEstadoNuevo = new PedidoImpresionEstado();
					pedidoImpresionEstadoNuevo.setFullNameUsername(usuarioEncargado.getApellido() + ", " + usuarioEncargado.getNombre());
					pedidoImpresionEstadoNuevo.setUsername(username);
					pedidoImpresionEstadoNuevo.setEstadoPedidoImpresion(EstadoPedidoImpresion.PARA_IMPRIMIR);
					
					PedidoImpresionEstado pedidoImpresionEstado = null;
					
					for(PedidoImpresionEstado o2: pedidoImpresion.get().getPedidoImpresionEstadoList()) {
						if(o2.getFechaFin() == null) {
							pedidoImpresionEstado = o2;
							break;
						}
					}
					
					pedidoImpresion.get().setFecha(new Date());
					
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

	public void rechazarPedidoImpresion(String username, Long idPedidoImpresion) {
		
		Usuario usuarioEncargado = usuarioDao.findByUsername(username);
		
		Optional<PedidoImpresion> pedidoImpresion = pedidoImpresionDao.findById(idPedidoImpresion);
		
		if(pedidoImpresion.isPresent()) {
			
			PedidoImpresionEstado pedidoImpresionEstadoNuevo = new PedidoImpresionEstado();
			pedidoImpresionEstadoNuevo.setFullNameUsername(usuarioEncargado.getApellido() + ", " + usuarioEncargado.getNombre());
			pedidoImpresionEstadoNuevo.setUsername(username);
			pedidoImpresionEstadoNuevo.setEstadoPedidoImpresion(EstadoPedidoImpresion.RECHAZADO);
			
			PedidoImpresionEstado pedidoImpresionEstado = null;
			
			for(PedidoImpresionEstado o: pedidoImpresion.get().getPedidoImpresionEstadoList()) {
				if(o.getFechaFin() == null) {
					pedidoImpresionEstado = o;
					break;
				}
			}
			
			pedidoImpresion.get().setFecha(new Date());
			
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
	

	public void confirmarPedido(String username, Long idPedidoImpresion) {
		
		Usuario usuarioEncargado = usuarioDao.findByUsername(username);
		
		Optional<PedidoImpresion> pedidoImpresion = pedidoImpresionDao.findById(idPedidoImpresion);
		
		if(pedidoImpresion.isPresent()) {
			
			PedidoImpresionEstado pedidoImpresionEstadoNuevo = new PedidoImpresionEstado();
			pedidoImpresionEstadoNuevo.setFullNameUsername(usuarioEncargado.getApellido() + ", " + usuarioEncargado.getNombre());
			pedidoImpresionEstadoNuevo.setUsername(username);
			pedidoImpresionEstadoNuevo.setEstadoPedidoImpresion(EstadoPedidoImpresion.PARA_RETIRAR);
			
			PedidoImpresionEstado pedidoImpresionEstado = null;
			
			for(PedidoImpresionEstado o: pedidoImpresion.get().getPedidoImpresionEstadoList()) {
				if(o.getFechaFin() == null) {
					pedidoImpresionEstado = o;
					break;
				}
			}
			
			pedidoImpresion.get().setFecha(new Date());
			
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
	
	public void entregarPedidoImpresion(String username, Long idPedidoImpresion) {
		
		Usuario usuarioEncargado = usuarioDao.findByUsername(username);
		
		Optional<PedidoImpresion> pedidoImpresion = pedidoImpresionDao.findById(idPedidoImpresion);
		
		if(pedidoImpresion.isPresent()) {
		
			PedidoImpresionEstado pedidoImpresionEstadoNuevo = new PedidoImpresionEstado();
			pedidoImpresionEstadoNuevo.setFullNameUsername(usuarioEncargado.getApellido() + ", " + usuarioEncargado.getNombre());
			pedidoImpresionEstadoNuevo.setUsername(username);
			pedidoImpresionEstadoNuevo.setEstadoPedidoImpresion(EstadoPedidoImpresion.RETIRADO);
			
			PedidoImpresionEstado pedidoImpresionEstado = null;
			
			for(PedidoImpresionEstado o: pedidoImpresion.get().getPedidoImpresionEstadoList()) {
				if(o.getFechaFin() == null) {
					pedidoImpresionEstado = o;
					break;
				}
			}
			
			pedidoImpresion.get().setFecha(new Date());

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
	
	private PedidoImpresionCentroDTO toPedidoImpresionCentroDTO(PedidoImpresion o){
		
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
						o.getNombreArchivoClave(),
						pedidoImpresionEstado.getEstadoPedidoImpresion().toString(),
						o.getCantidadCopias(),
						o.getCantidadHojas(),
						o.isDobleFax(),
						o.isColor(),
						o.getTamanioPapel(),
						o.getDisenio());
		
		return pedidoImpresionCentroDTO;
	}
	

}
