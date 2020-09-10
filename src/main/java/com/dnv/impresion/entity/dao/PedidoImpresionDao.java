package com.dnv.impresion.entity.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.dnv.impresion.models.entity.PedidoImpresion;
import com.dnv.impresion.models.entity.Usuario;

public interface PedidoImpresionDao extends CrudRepository<PedidoImpresion, Long>{
	
	public List<PedidoImpresion> findAll();

	public List<PedidoImpresion> findByUsuario(Usuario usuario);
			
	public Optional<PedidoImpresion> findById(Long id);
	
}
