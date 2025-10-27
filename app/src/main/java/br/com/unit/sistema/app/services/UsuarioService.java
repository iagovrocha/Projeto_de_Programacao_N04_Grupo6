package br.com.unit.sistema.app.services;

import br.com.unit.sistema.app.controller.dto.CriarUsuarioDto;
import br.com.unit.sistema.app.entity.Usuario;
import br.com.unit.sistema.app.repository.UsuarioRepository;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void criarUsuario (CriarUsuarioDto criarUsuarioDto) {
        var usuario = new Usuario(null, criarUsuarioDto.email(), criarUsuarioDto.senha());

        usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
}