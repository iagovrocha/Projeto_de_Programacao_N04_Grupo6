package br.com.unit.sistema.app.services;

import br.com.unit.sistema.app.controller.dto.UsuarioCreateDTO;
import br.com.unit.sistema.app.controller.dto.UsuarioResponseDTO;
import br.com.unit.sistema.app.entity.UsuarioEntidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import br.com.unit.sistema.app.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void criarUsuario (UsuarioCreateDTO usuarioCreateDTO) {
        var usuario = new UsuarioEntidade();
        usuario.setEmail(usuarioCreateDTO.getEmail());
        usuario.setNome(usuarioCreateDTO.getNome());
        usuario.setSenha(usuarioCreateDTO.getSenha());

        usuarioRepository.save(usuario);
    }

     public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
        .stream()
        .map(usuario -> new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail()
        ))
        .toList();
    }
}