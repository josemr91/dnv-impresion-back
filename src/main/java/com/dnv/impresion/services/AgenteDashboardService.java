package com.dnv.impresion.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dnv.impresion.dto.pedidosImpresion.PedidoImpresionAgenteDTO;

public interface AgenteDashboardService {

	public void solicitarImpresion(MultipartFile file, String fileNameClave, String username, int cantidadCopias,
			boolean dobleFax, boolean color, String disenio, String tamanioPapel) throws IOException;
	
	public List<PedidoImpresionAgenteDTO> obtenerPedidosImpresionByAgente(String username);
	
	public void cancelarPedidoImpresion(Long idPedidoImpresion) throws Exception;
	
	
}
