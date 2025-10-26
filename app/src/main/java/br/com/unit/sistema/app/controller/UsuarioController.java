package br.com.unit.sistema.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import br.com.unit.sistema.app.entity.UsuarioEntidade;
import br.com.unit.sistema.app.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<UsuarioEntidade> listarTodos() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public UsuarioEntidade buscarPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @PostMapping
    public UsuarioEntidade criarUsuario(@RequestBody UsuarioEntidade usuario) {
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioEntidade atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioEntidade usuarioAtualizado) {
        UsuarioEntidade usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setSenha(usuarioAtualizado.getSenha());
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }
}
