package com.dnv.impresion.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dnv.impresion.dto.pedidosImpresion.PedidoImpresionCentroDTO;
import com.dnv.impresion.models.entity.PedidoImpresion;
import com.dnv.impresion.services.CentroCopiadoDashboardService;

@RestController
@RequestMapping("/api/centroCopiadoDashboard")
public class CentroCopiadoDashboardController {
	
	@Autowired
	CentroCopiadoDashboardService centroCopiadoDashboardService;
		
	@Secured({"ROLE_ADMIN","ROLE_CCYD"})
	@GetMapping("obtenerPedidosImpresion/{tipoEstado}/{page}")
	public ResponseEntity<?> obtenerPedidosImpresion(@PathVariable String tipoEstado, @PathVariable Integer page){
		
		Map<String, Object> response = new HashMap<>();
		Page<PedidoImpresionCentroDTO> pedidoImpresionCentroDTOPage = null;
		
		int pedidosPorPagina = 10;
		
		try {
			pedidoImpresionCentroDTOPage = centroCopiadoDashboardService.obtenerPedidosImpresion(tipoEstado, PageRequest.of(page, pedidosPorPagina));
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al obtener los pedidos.");
			response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		
		return new ResponseEntity<Page<PedidoImpresionCentroDTO>>(pedidoImpresionCentroDTOPage, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_CCYD"})
	@PostMapping("asignarPedidoImpresion")
	public ResponseEntity<?> asignarPedidoImpresion(@RequestParam("username") String username,
			@RequestParam("idPedidoImpresion") Long idPedidoImpresion) throws Exception{
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			centroCopiadoDashboardService.asignarPedidoImpresion(username, idPedidoImpresion);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al asignar el Pedido de impresion.");
			response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
		} 
	
		response.put("mensaje", "El Pedido de impresion fue asignado correctamente.");
	
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_CCYD"})
	@PostMapping("confirmarImpresionPedido")
	public ResponseEntity<?> confirmarImpresionPedido(@RequestParam("username") String username,
														@RequestParam("idPedido") Long idPedido){
				
		Map<String, Object> response = new HashMap<>();
		
		try {
			centroCopiadoDashboardService.confirmarPedido(username, idPedido);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al asignar el Pedido de impresion.");
			response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
		} 
		
		response.put("mensaje", "El Pedido fue confirmado con exito.");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
		
	}
	
	@Secured({"ROLE_ADMIN","ROLE_CCYD"})
	@PostMapping("rechazarPedido")
	public ResponseEntity<?> rechazarPedido(@RequestParam("username") String username,
											@RequestParam("idPedido") Long idPedidoImpresion){
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			centroCopiadoDashboardService.rechazarPedidoImpresion(username, idPedidoImpresion);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al rechazar el Pedido de impresion.");
			response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		
		response.put("mensaje", "El Pedido fue rechazado.");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
	}	
	
	@Secured({"ROLE_ADMIN","ROLE_CCYD"})
	@PostMapping("entregarPedidoImpresion")
	public ResponseEntity<?> entregarPedidoImpresion(@RequestParam("username") String username,
														@RequestParam("idPedido") Long idPedido){
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			centroCopiadoDashboardService.entregarPedidoImpresion(username, idPedido);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al asignar el Pedido de impresion.");
			response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
		} 
		
		response.put("mensaje", "El Pedido fue entregado.");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
	}
	
	
	
	

}
