package com.dnv.impresion.services;


import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
	
	public void storeFile(MultipartFile archivo, String randomFileName) throws IOException;

	public Resource loadFileAsResource(Long idPedido) throws MalformedURLException, Exception;
	
	//public Resource loadFileAsResource(String fileName) throws MalformedURLException;
}
