package br.com.unit.sistema.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import br.com.unit.sistema.app.controller.dto.UsuarioAutenticacaoDTO;
import br.com.unit.sistema.app.controller.dto.UsuarioResponseDTO;
import br.com.unit.sistema.app.entity.UsuarioEntidade;
import br.com.unit.sistema.app.repository.UsuarioRepository;
import br.com.unit.sistema.app.services.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

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

    @PostMapping("/login")
    public UsuarioResponseDTO fazerLogin(@RequestBody @Valid UsuarioAutenticacaoDTO dadosLogin){
        return usuarioService.autenticarUsuario(dadosLogin);
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
