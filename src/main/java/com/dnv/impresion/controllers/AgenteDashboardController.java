package com.dnv.impresion.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dnv.impresion.dto.pedidosImpresion.PedidoImpresionAgenteDTO;
import com.dnv.impresion.services.AgenteDashboardService;
import com.dnv.impresion.services.FileStorageServiceImpl;

@RestController
@RequestMapping("/api/agenteDashboard")
public class AgenteDashboardController {
	
	@Autowired
	AgenteDashboardService agenteDashboardService;
	
	@Autowired
	FileStorageServiceImpl fileStorageService;
	
	//private static final Logger logger = LoggerFactory.getLogger(AgenteDashboardController.class);
	
	@Secured({"ROLE_ADMIN","ROLE_CCYD","ROLE_AGENTE"})
	@PostMapping("solicitarImpresion")
	public ResponseEntity<?> solicitarImpresion(@RequestParam("file") MultipartFile file,
														  @RequestParam("username") String username,
														  @RequestParam("dobleFaz") boolean dobleFaz,
														  @RequestParam("color") boolean color,
														  @RequestParam("cantidadCopias") int cantidadCopias,
														  @RequestParam("disenio") String disenio,
														  @RequestParam("tamanioPapel") String tamanioPapel,
														  @RequestParam("randomFileName") String randomFileName){

		Map<String, Object> response = new HashMap<>();
				
		if(!file.isEmpty()){
			
			try {				
				//fileStorageService.storeFile(file);
				agenteDashboardService.solicitarImpresion(file, randomFileName, username, cantidadCopias, dobleFaz, color, disenio, tamanioPapel);
			}catch (IOException e) {
				e.printStackTrace();
				response.put("mensaje", "Error al subir el archivo");
				response.put("error", e.getMessage());
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
			}
			response.put("mensaje", "Has enviado correctamente el archivo: " + randomFileName);
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	

	@Secured({"ROLE_ADMIN","ROLE_CCYD","ROLE_AGENTE"})
	@PatchMapping("cancelarPedidoImpresion")
	public ResponseEntity<?> cancelarPedidoImpresion(@RequestParam("idPedidoImpresion") Long idPedidoImpresion) throws Exception {
		
		Map<String, Object> response = new HashMap<>();
		try {
			agenteDashboardService.cancelarPedidoImpresion(idPedidoImpresion);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el pedido de impresion.");
			response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		
		response.put("mensaje", "Se ha cancelado el pedido de impresion correctamente.");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	

	@Secured({"ROLE_ADMIN","ROLE_CCYD","ROLE_AGENTE"})
	@GetMapping("listaPedidosAgente/{username}/{page}")
	public ResponseEntity<?> pedidosImpresionPorAgente(@PathVariable String username, @PathVariable Integer page){
		
		int pedidosPorPagina = 10;
		
		Map<String, Object> response = new HashMap<>();
		Page<PedidoImpresionAgenteDTO> pedidoImpresionAgenteDtoPage = null;
				
		try {
			pedidoImpresionAgenteDtoPage = agenteDashboardService.obtenerPedidosImpresionByAgente(username, PageRequest.of(page, pedidosPorPagina, Sort.by("fecha").descending()));
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al obtener los pedidos de impresion.");
			response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		
		return new ResponseEntity<Page<PedidoImpresionAgenteDTO>>(pedidoImpresionAgenteDtoPage, HttpStatus.CREATED);
	}


	@Secured({"ROLE_ADMIN","ROLE_CCYD","ROLE_AGENTE"})
	@PostMapping("setFeedbackPedido")
	public ResponseEntity<?> setFeedbackpedido(@RequestParam("idPedidoImpresion") Long idPedidoImpresion,
												@RequestParam("feedbackPedido") String feedbackPedido){
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			agenteDashboardService.setFeedbackPedido(idPedidoImpresion, feedbackPedido);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al calificar pedido.");
			response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		
		response.put("mensaje", "Se ha calificado el pedido correctamente.");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
}
