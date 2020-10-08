package com.dnv.impresion.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dnv.impresion.entity.dao.UsuarioDao;
import com.dnv.impresion.models.entity.Usuario;

@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService{
	
	private Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	@Autowired
	private UsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = usuarioDao.findByUsername(username);
		
		if(usuario == null) {
			logger.error("Error en el login: No existe el usuario: " + username);
			throw new UsernameNotFoundException("Error en el login: No existe el usuario: " + username);
		}
		
		List<GrantedAuthority> authorities = usuario.getRoleList()
													.stream()
													.map(role -> new SimpleGrantedAuthority(role.getNombre()))
													.peek(authority -> logger.info("Role: " + authority.getAuthority()))
													.collect(Collectors.toList());
				
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(), true, true, true, authorities);
	}

	@Override
	@Transactional(readOnly=true)
	public Usuario findByUsername(String username) {
		
		Usuario usuario = usuarioDao.findByUsername(username);
		return usuario;
	}

}
