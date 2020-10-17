package com.dnv.impresion.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dnv.impresion.entity.dao.PedidoImpresionDao;
import com.dnv.impresion.models.entity.PedidoImpresion;

@Service
public class FileStorageServiceImpl implements FileStorageService{
	
	@Autowired
	PedidoImpresionDao pedidoImpresionDao;
	
	private final Logger log = LoggerFactory.getLogger(AgenteDashboardService.class);
	
	private final static String DIRECTORIO_UPLOAD = "uploadsArchivosImpresion";

	public Resource loadFileAsResource(Long idPedido) throws MalformedURLException, Exception{
        	
			Optional<PedidoImpresion> pedidoImpresion = pedidoImpresionDao.findById(idPedido);
			
			Resource resource = null;
			
			if(pedidoImpresion.isPresent()) {
				Path filePath = this.getPath(pedidoImpresion.get().getNombreArchivoClave());
	            resource = new UrlResource(filePath.toUri());
			}else {
				throw new Exception("Error al obtener el archivo.");
			}
			
            return resource;

    }
	
	public String storeFile(MultipartFile archivo) throws IOException{
		
		String nombreArchivoClave = UUID.randomUUID().toString() + "_" +  archivo.getOriginalFilename().replace(" ", "");
		
		// Check if the file's name contains invalid characters
    	/*
        if(fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }
		*/
		
		Path rutaArchivo = getPath(nombreArchivoClave);
		log.info(rutaArchivo.toString());
		
		Files.copy(archivo.getInputStream(), rutaArchivo);		
		
		return nombreArchivoClave;
	}
	
	// Private
	
	private Path getPath(String nombreFoto) {
		return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
	}
}
