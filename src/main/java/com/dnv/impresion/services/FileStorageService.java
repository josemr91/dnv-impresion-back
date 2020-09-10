package com.dnv.impresion.services;


import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
	
	public String storeFile(MultipartFile archivo) throws IOException;

	public Resource loadFileAsResource(Long idPedido) throws MalformedURLException;
	
	//public Resource loadFileAsResource(String fileName) throws MalformedURLException;
}
