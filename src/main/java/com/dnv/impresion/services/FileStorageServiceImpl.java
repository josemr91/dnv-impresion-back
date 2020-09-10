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

	// ????
	
	/*@Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
    }*/
	
	//Ver las excepciones
	
	public Resource loadFileAsResource(Long idPedido) throws MalformedURLException{
        	
			Optional<PedidoImpresion> pedidoImpresion = pedidoImpresionDao.findById(idPedido);
		
            Path filePath = this.getPath(pedidoImpresion.get().getNombreArchivoClave());
            System.out.println(filePath.toString());
            Resource resource = new UrlResource(filePath.toUri());
            
            //Verificar si existe
            return resource;

    }
	
	// OK
	public String storeFile(MultipartFile archivo) throws IOException{
		
		String nombreArchivoClave = UUID.randomUUID().toString() + "_" +  archivo.getOriginalFilename().replace(" ", "");
		//String nombreArchivo = archivo.getOriginalFilename().replace(" ", "");
		
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
	
	//OK
	private Path getPath(String nombreFoto) {
		return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
	}
}
