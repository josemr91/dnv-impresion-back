package com.dnv.impresion.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dnv.impresion.dto.pedidosImpresion.PedidoImpresionCentroDTO;

public interface CentroCopiadoDashboardService {
	
	public Page<PedidoImpresionCentroDTO> obtenerPedidosImpresion(String tipoEstado, Pageable pageable);
	
	public void asignarPedidoImpresion(String username, Long idPedidoImpresion) throws Exception;
	
	public void rechazarPedidoImpresion(String username, Long idPedidoImpresion);
	
	public void confirmarPedido(String username, Long idPedidoImpresion);
	
	public void entregarPedidoImpresion(String username, Long idPedidoImpresion);
	

}
