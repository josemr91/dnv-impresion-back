package com.dnv.impresion.controllers;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnv.impresion.services.FileStorageService;

@RestController
@RequestMapping("/api/fileStorage")
public class FileStorageController {
	
	@Autowired
	FileStorageService fileStorageService;
	
	@GetMapping("/files/{idPedido}")
	public ResponseEntity<?> serveFile(@PathVariable Long idPedido) throws Exception {
		
	    Resource file = null;
		try {
			file = fileStorageService.loadFileAsResource(idPedido);
		} catch (MalformedURLException e) {
			
			Map<String, Object> response = new HashMap<>();
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
		
			Map<String, Object> response = new HashMap<>();
			response.put("mensaje", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	    return ResponseEntity
	            .ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
	            .body(file);
	}

}
