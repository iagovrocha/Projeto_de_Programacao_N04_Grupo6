package br.com.unit.sistema.app.services;

import br.com.unit.sistema.app.controller.dto.CriarUsuarioDto;
import br.com.unit.sistema.app.entity.UsuarioX;
import br.com.unit.sistema.app.repository.UsuarioRepositoryX;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UsuarioServiceX {
    private UsuarioRepositoryX usuarioRepositoryX;

    public UsuarioServiceX(UsuarioRepositoryX usuarioRepositoryX) {
        this.usuarioRepositoryX = usuarioRepositoryX;
    }

    public void criarUsuario (CriarUsuarioDto criarUsuarioDto) {
        var usuario = new UsuarioX(null, criarUsuarioDto.email(), criarUsuarioDto.senha());

        usuarioRepositoryX.save(usuario);
    }

    public List<UsuarioX> listarUsuarios() {
        return usuarioRepositoryX.findAll();
    }
}