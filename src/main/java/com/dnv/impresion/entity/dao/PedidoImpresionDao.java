package com.dnv.impresion.entity.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dnv.impresion.models.entity.PedidoImpresion;
import com.dnv.impresion.models.entity.Usuario;

public interface PedidoImpresionDao extends JpaRepository<PedidoImpresion, Long>{
	
	public List<PedidoImpresion> findAll();
	
	public Page<PedidoImpresion> findAll(Pageable pageable);
	
	public List<PedidoImpresion> findByUsuario(Usuario usuario);
			
	public Page<PedidoImpresion> findByUsuario(Usuario usuario, Pageable pageable);
	
	public Optional<PedidoImpresion> findById(Long id);
	
	@Query(value="SELECT p FROM PedidoImpresion p JOIN FETCH p.pedidoImpresionEstadoList e WHERE e.fechaFin IS null AND e.estadoPedidoImpresion IN ('SIN_ASIGNAR','PARA_IMPRIMIR','PARA_RETIRAR')")
	public List<PedidoImpresion> obtenerPedidosIntermedios();
	
}
