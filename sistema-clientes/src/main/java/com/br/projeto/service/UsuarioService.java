package com.br.projeto.service;

import com.br.projeto.entity.Usuario;
import com.br.projeto.repository.UsuarioRepository;
import com.br.projeto.rest.exception.UsuarioCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvar(Usuario usuario){

        boolean existe = usuarioRepository.existsByUserName(usuario.getUserName());

        if(existe){

            throw new UsuarioCadastradoException(usuario.getUserName());
        }

       return usuarioRepository.save(usuario);
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByUserName(userName)
                .orElseThrow(()-> new UsernameNotFoundException("Login não encontrado ."));
        return User.builder()
                .username(usuario.getUserName())
                .password(usuario.getPassword())
                .roles("USER")
                .build();
    }
}
