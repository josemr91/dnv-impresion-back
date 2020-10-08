package com.dnv.impresion.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dnv.impresion.dto.pedidosImpresion.PedidoImpresionAgenteDTO;
import com.dnv.impresion.entity.dao.PedidoImpresionDao;
import com.dnv.impresion.entity.dao.PedidoImpresionEstadoDao;
import com.dnv.impresion.entity.dao.UsuarioDao;
import com.dnv.impresion.models.entity.PedidoImpresion;
import com.dnv.impresion.models.entity.PedidoImpresionEstado;
import com.dnv.impresion.models.entity.Usuario;

@Service
public class AgenteDashboardServiceImpl implements AgenteDashboardService{

	@Autowired
	UsuarioDao usuarioDao;

	@Autowired
	PedidoImpresionDao pedidoImpresionDao;
	
	@Autowired
	PedidoImpresionEstadoDao pedidoImpresionEstadoDao;

	@Override
	public void solicitarImpresion(MultipartFile file, String fileNameClave, String username, int cantidadCopias,
			boolean dobleFax, boolean color, String disenio, String tamanioPapel) throws IOException {

		Usuario usuario = usuarioDao.findByUsername(username);

		String nombreArchivo = file.getOriginalFilename().replace(" ", "");

		PedidoImpresion pedidoImpresion = new PedidoImpresion();

		pedidoImpresion.setUsuario(usuario);
		pedidoImpresion.setNombreArchivoClave(fileNameClave);
		pedidoImpresion.setNombreArchivoImpresion(nombreArchivo);
		pedidoImpresion.setColor(color);
		pedidoImpresion.setDobleFax(dobleFax);
		pedidoImpresion.setCantidadCopias(cantidadCopias);
		pedidoImpresion.setDisenio(disenio);
		pedidoImpresion.setTamanioPapel(tamanioPapel);

		int cantidadHojas = this.calcularCantidadHojas(file, dobleFax, cantidadCopias);
		pedidoImpresion.setCantidadHojas(cantidadHojas);

		//PedidoImpresionEstado
		PedidoImpresionEstado pedidoImpresionEstado = new PedidoImpresionEstado();
		pedidoImpresionEstado.setEstadoPedidoImpresion(com.dnv.impresion.models.entity.PedidoImpresionEstado.EstadoPedidoImpresion.SIN_ASIGNAR);
		pedidoImpresion.addPedidoImpresionEstado(pedidoImpresionEstado);
		
		//Save
		pedidoImpresionEstadoDao.save(pedidoImpresionEstado);
		pedidoImpresionDao.save(pedidoImpresion);

	}

	public List<PedidoImpresionAgenteDTO> obtenerPedidosImpresionByAgente(String username) {

		Usuario usuario = usuarioDao.findByUsername(username);

		List<PedidoImpresionAgenteDTO> pedidoImpresionDTOList = new ArrayList<>();

		List<PedidoImpresion> pedidoImpresionList = pedidoImpresionDao.findByUsuario(usuario);
		
		for (PedidoImpresion o : pedidoImpresionList) {
			
			List <PedidoImpresionEstado> pedidoImpresionEstadoList = o.getPedidoImpresionEstadoList();
			PedidoImpresionEstado pedidoImpresionEstado = null;
			
			for(PedidoImpresionEstado o2 : pedidoImpresionEstadoList) {
				if(o2.getFechaFin() == null) {
					pedidoImpresionEstado = o2;
					break;
				}
				
			}
			
			String feedbackPedido;
			
			if(o.getFeedbackPedido() == null) {
				feedbackPedido = "";
			}else {
				feedbackPedido = o.getFeedbackPedido().toString();
			}
			
			PedidoImpresionAgenteDTO pedidoImpresionDTO = new PedidoImpresionAgenteDTO(o.getId(), o.getFecha(),
					o.getNombreArchivoImpresion(), 
					pedidoImpresionEstado.getEstadoPedidoImpresion().toString(), 
					o.getCantidadCopias(),
					o.getCantidadHojas(), o.getTamanioPapel(), o.isDobleFax(), o.isColor(), o.getDisenio(), feedbackPedido);

			pedidoImpresionDTOList.add(pedidoImpresionDTO);
		}

		return pedidoImpresionDTOList;

	}

	public void cancelarPedidoImpresion(Long idPedidoImpresion) throws Exception {
		
		Optional<PedidoImpresion> pedidoImpresion = pedidoImpresionDao.findById(idPedidoImpresion);

		if (pedidoImpresion.isPresent()) {
			
			PedidoImpresionEstado pedidoImpresionEstado = this.obtenerPedidoImpresionEstado(pedidoImpresion.get());
			
			if(pedidoImpresionEstado.getEstadoPedidoImpresion().equals(com.dnv.impresion.models.entity.PedidoImpresionEstado.EstadoPedidoImpresion.SIN_ASIGNAR)){
								
				pedidoImpresionEstado.setFechaFin(new Date());
				
				PedidoImpresionEstado pedidoImpresionEstadoNuevo = new PedidoImpresionEstado();
				pedidoImpresionEstadoNuevo.setEstadoPedidoImpresion(com.dnv.impresion.models.entity.PedidoImpresionEstado.EstadoPedidoImpresion.CANCELADO);
				
				pedidoImpresion.get().addPedidoImpresionEstado(pedidoImpresionEstadoNuevo);
				
				//Save
				pedidoImpresionEstadoDao.save(pedidoImpresionEstadoNuevo);
				pedidoImpresionEstadoDao.save(pedidoImpresionEstado);
				pedidoImpresionDao.save(pedidoImpresion.get());
				
			} else {
								
				throw new Exception("El pedido se encuentra en proceso de impresion, para cancelar contactar urgente con el centro de copiado");
			}

			
		}

	}

	public void setFeedbackPedido(Long idPedidoImpresion, String feedBack) {
		
		Optional<PedidoImpresion> pedidoImpresion = pedidoImpresionDao.findById(idPedidoImpresion);
		
		if(pedidoImpresion.isPresent()) {
			
			if(feedBack.equals("Positivo")){
				pedidoImpresion.get().setFeedbackPedido(PedidoImpresion.FeedbackPedido.POSITIVO);
			} else {
				pedidoImpresion.get().setFeedbackPedido(PedidoImpresion.FeedbackPedido.NEGATIVO);
			}
			
			PedidoImpresionEstado pedidoImpresionEstado = this.obtenerPedidoImpresionEstado(pedidoImpresion.get());
			
			pedidoImpresionEstado.setFechaFin(new Date());
			PedidoImpresionEstado pedidoImpresionEstadoNuevo = new PedidoImpresionEstado();
			pedidoImpresionEstadoNuevo.setEstadoPedidoImpresion(PedidoImpresionEstado.EstadoPedidoImpresion.FINALIZADO);
			pedidoImpresion.get().addPedidoImpresionEstado(pedidoImpresionEstadoNuevo);
			
			//Save
			pedidoImpresionEstadoDao.save(pedidoImpresionEstadoNuevo);
			pedidoImpresionEstadoDao.save(pedidoImpresionEstado);
			pedidoImpresionDao.save(pedidoImpresion.get());
						
		} else {
			throw new NullPointerException("No se encuentra el pedido seleccionado");
		}
		
		
	}

	// Private
		
	private int calcularCantidadHojas(MultipartFile file, boolean dobleFax, int cantidadCopias) throws IOException {

		PDDocument doc = PDDocument.load(file.getInputStream());
		int cantidadPaginas = doc.getNumberOfPages();
		doc.close();

		int cantidadHojas;

		if (dobleFax) {
			cantidadHojas = (cantidadPaginas / 2) + 1;
		} else {
			cantidadHojas = cantidadPaginas;
		}

		return cantidadHojas * cantidadCopias;

	}

	private PedidoImpresionEstado obtenerPedidoImpresionEstado(PedidoImpresion pedidoImpresion) {
		
		List <PedidoImpresionEstado> pedidoImpresionEstadoList = pedidoImpresion.getPedidoImpresionEstadoList();
		PedidoImpresionEstado pedidoImpresionEstado = null;
		
		for(PedidoImpresionEstado o : pedidoImpresionEstadoList) {
			if(o.getFechaFin() == null) {
				pedidoImpresionEstado = o;
				break;
			}
			
		}
		
		return pedidoImpresionEstado;
	}
	
	// Prueba Page con DTO
	
	public Page<PedidoImpresionAgenteDTO> pruebaObtenerPagePedidosImpresionByAgente(String username, Pageable pageable){
		
		Usuario usuario = usuarioDao.findByUsername(username);
		
		Page<PedidoImpresion> pedidoImpresionPage = pedidoImpresionDao.findByUsuario(usuario, pageable);
		
		return toPagePedidoImpresionAgenteDTO(pedidoImpresionPage);
		
	}
	
	private PedidoImpresionAgenteDTO convertToPedidoImpresionAgenteDTO(PedidoImpresion o) {

		List <PedidoImpresionEstado> pedidoImpresionEstadoList = o.getPedidoImpresionEstadoList();
		PedidoImpresionEstado pedidoImpresionEstado = null;
		
		for(PedidoImpresionEstado o2 : pedidoImpresionEstadoList) {
			if(o2.getFechaFin() == null) {
				pedidoImpresionEstado = o2;
				break;
			}
			
		}
		
		String feedbackPedido;
		
		if(o.getFeedbackPedido() == null) {
			feedbackPedido = "";
		}else {
			feedbackPedido = o.getFeedbackPedido().toString();
		}
		
		PedidoImpresionAgenteDTO pedidoImpresionDTO = new PedidoImpresionAgenteDTO(o.getId(), o.getFecha(),
				o.getNombreArchivoImpresion(), pedidoImpresionEstado.getEstadoPedidoImpresion().toString(), o.getCantidadCopias(),
				o.getCantidadHojas(), o.getTamanioPapel(), o.isDobleFax(), o.isColor(), o.getDisenio(), feedbackPedido);
    
		

	    return pedidoImpresionDTO;
	}
	
	private Page<PedidoImpresionAgenteDTO> toPagePedidoImpresionAgenteDTO(Page<PedidoImpresion> objects){
		Page<PedidoImpresionAgenteDTO> dtos = objects.map(this::convertToPedidoImpresionAgenteDTO);
		return dtos;
	}
	

	
}
