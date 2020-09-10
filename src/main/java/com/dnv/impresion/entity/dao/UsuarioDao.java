package com.dnv.impresion.entity.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.dnv.impresion.models.entity.Usuario;

public interface UsuarioDao extends CrudRepository<Usuario, Long> {
	
	public Usuario findByUsername(String username);
	
	public Optional<Usuario> findById(Long id);

}
