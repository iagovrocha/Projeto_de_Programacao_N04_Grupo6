package br.com.unit.sistema.app.services;

// import br.com.unit.sistema.app.controller.dto.CriarUsuarioDto;
// import br.com.unit.sistema.app.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unit.sistema.app.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    // public void criarUsuario (CriarUsuarioDto criarUsuarioDto) {
    //     var usuario = new Usuario(null, criarUsuarioDto.email(), criarUsuarioDto.senha());

    //     usuarioRepository.save(usuario);
    // }

    // public List<Usuario> listarUsuarios() {
    //     return usuarioRepository.findAll();
    // }
}