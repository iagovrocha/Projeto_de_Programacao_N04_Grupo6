package br.com.unit.sistema.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.unit.sistema.app.controller.dto.AtualizarUsuarioDTO;
import br.com.unit.sistema.app.controller.dto.UsuarioAutenticacaoDTO;
import br.com.unit.sistema.app.controller.dto.UsuarioCreateDTO;
import br.com.unit.sistema.app.controller.dto.UsuarioResponseDTO;
import br.com.unit.sistema.app.entity.UsuarioEntidade;
import br.com.unit.sistema.app.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Transactional
    public void criarUsuario (UsuarioCreateDTO usuarioCreateDTO) {
        usuarioRepository.save(new UsuarioEntidade(usuarioCreateDTO));
    }

    @Transactional
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(UsuarioCreateDTO usuario) {
        // Validar se já existe um usuário com o mesmo email
        boolean emailJaExiste = usuarioRepository.findAll()
            .stream()
            .anyMatch(u -> u.getEmail().equals(usuario.email()));
        
        if (emailJaExiste) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        
        // Criar novo usuário
        UsuarioEntidade novoUsuario = new UsuarioEntidade(usuario);
        usuarioRepository.save(novoUsuario);
        
        UsuarioResponseDTO response = new UsuarioResponseDTO(
            novoUsuario.getId(),
            novoUsuario.getNome(),
            novoUsuario.getEmail(),
            novoUsuario.getRole()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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

    @Transactional
    public UsuarioResponseDTO atualizar(Long id, AtualizarUsuarioDTO dados) {
        UsuarioEntidade usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        usuario.atualizarInfo(dados);
        usuarioRepository.save(usuario);
        
        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getRole()
        );
    }
}