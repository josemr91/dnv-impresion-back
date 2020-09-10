package com.dnv.impresion.controllers;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnv.impresion.entity.dao.PedidoImpresionDao;
import com.dnv.impresion.services.FileStorageService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/prueba")
public class PruebaController {
	
	@Autowired
	FileStorageService fileStorageService;
	
	
	@GetMapping("/files/{idPedido}")
	public ResponseEntity<Resource> serveFile(@PathVariable Long idPedido) {

	    Resource file = null;
		try {
			file = fileStorageService.loadFileAsResource(idPedido);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return ResponseEntity
	            .ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
	            .body(file);
	}

}
