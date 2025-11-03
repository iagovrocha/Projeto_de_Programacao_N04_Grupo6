package br.com.unit.sistema.app.services;

import br.com.unit.sistema.app.controller.dto.UsuarioAutenticacaoDTO;
import br.com.unit.sistema.app.controller.dto.UsuarioCreateDTO;
import br.com.unit.sistema.app.controller.dto.UsuarioResponseDTO;
import br.com.unit.sistema.app.entity.UsuarioEntidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import br.com.unit.sistema.app.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Transactional
    public void criarUsuario (UsuarioCreateDTO usuarioCreateDTO) {
        var usuario = new UsuarioEntidade();
        usuario.setEmail(usuarioCreateDTO.email());
        usuario.setNome(usuarioCreateDTO.nome());
        usuario.setSenha(usuarioCreateDTO.senha());

        usuarioRepository.save(usuario);
    }
    @Transactional
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
        .stream()
        .map(usuario -> new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getRole()
        ))
        .toList();
    }
    public UsuarioResponseDTO autenticarUsuario(UsuarioAutenticacaoDTO validarLogin){
        UsuarioEntidade usuario = usuarioRepository.findAll()
        .stream()
        .filter(
            u -> u.getEmail().equals(validarLogin.email()) && u.getSenha().equals(validarLogin.senha())
        ).findFirst()
        .orElse(null);
        if (usuario == null){
            return null;
        }else{
            UsuarioResponseDTO usuarioResposta = new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getRole());
            return usuarioResposta;
        }
    }
}