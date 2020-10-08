package com.dnv.impresion.entity.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dnv.impresion.models.entity.PedidoImpresion;
import com.dnv.impresion.models.entity.Usuario;

public interface PedidoImpresionDao extends JpaRepository<PedidoImpresion, Long>{
	
	public List<PedidoImpresion> findAll();
	
	public Page<PedidoImpresion> findAll(Pageable pageable);
	
	public List<PedidoImpresion> findByUsuario(Usuario usuario);
			
	public Page<PedidoImpresion> findByUsuario(Usuario usuario, Pageable pageable);
	
	public Optional<PedidoImpresion> findById(Long id);
	
}
