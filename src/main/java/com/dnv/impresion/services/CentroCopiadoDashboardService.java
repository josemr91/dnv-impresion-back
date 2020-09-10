package com.dnv.impresion.services;

import java.util.List;

import com.dnv.impresion.dto.pedidosImpresion.PedidoImpresionCentroDTO;

public interface CentroCopiadoDashboardService {
	
	public List<PedidoImpresionCentroDTO> obtenerPedidosImpresion(String tipoEstado);
	
	public void asignarPedidoImpresion(String username, Long idPedidoImpresion) throws Exception;
	
	public void rechazarPedidoImpresion(String username, Long idPedidoImpresion);
	
	public void confirmarPedido(String username, Long idPedidoImpresion);
	
	public void entregarPedidoImpresion(String username, Long idPedidoImpresion);

}
