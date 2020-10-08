package com.dnv.impresion.services;

import com.dnv.impresion.models.entity.Usuario;

public interface UsuarioService {
	
	public Usuario findByUsername(String username);

}
