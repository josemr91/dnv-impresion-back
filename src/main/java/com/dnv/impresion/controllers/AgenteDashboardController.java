package com.dnv.impresion.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	
	private static final Logger logger = LoggerFactory.getLogger(AgenteDashboardController.class);
	
	@Secured({"ROLE_ADMIN","ROLE_CCYD","ROLE_AGENTE"})
	@PostMapping("solicitarImpresion")
	public ResponseEntity<?> solicitarImpresion(@RequestParam("file") MultipartFile file,
														  @RequestParam("username") String username,
														  @RequestParam("dobleFaz") boolean dobleFaz,
														  @RequestParam("color") boolean color,
														  @RequestParam("cantidadCopias") int cantidadCopias,
														  @RequestParam("disenio") String disenio,
														  @RequestParam("tamanioPapel") String tamanioPapel){

		Map<String, Object> response = new HashMap<>();
				
		if(!file.isEmpty()){
			
			String fileNameClave = null;
			
			try {
				fileNameClave = fileStorageService.storeFile(file);
				agenteDashboardService.solicitarImpresion(file, fileNameClave, username, cantidadCopias, dobleFaz, color, disenio, tamanioPapel);
			}catch (IOException e) {
				e.printStackTrace();
				response.put("mensaje", "Error al subir el archivo");
				response.put("error", e.getMessage());
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
			}
			response.put("mensaje", "Has enviado correctamente el archivo: " + fileNameClave);
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
		
		Map<String, Object> response = new HashMap<>();
		List<PedidoImpresionAgenteDTO> pedidoImpresionDtoList = new ArrayList<>();
		Page<PedidoImpresionAgenteDTO> pageDto = null;
		try {
			
			pageDto = agenteDashboardService.pruebaObtenerPagePedidosImpresionByAgente(username, PageRequest.of(page, 2));
			
			pedidoImpresionDtoList = agenteDashboardService.obtenerPedidosImpresionByAgente(username);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al obtener los pedidos de impresion.");
			response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		
		//return new ResponseEntity<List<PedidoImpresionAgenteDTO>>(pedidoImpresionDtoList, HttpStatus.CREATED);
		return new ResponseEntity<Page<PedidoImpresionAgenteDTO>>(pageDto, HttpStatus.CREATED);
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
	
	
	/*
	// Prueba
	@GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
		
        Resource resource = null;
		try {
			resource = fileStorageService.loadFileAsResource(fileName);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }*/

}
