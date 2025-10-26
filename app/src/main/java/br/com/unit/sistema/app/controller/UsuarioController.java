package br.com.unit.sistema.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import br.com.unit.sistema.app.entity.UsuarioEntidade;
import br.com.unit.sistema.app.repository.UsuarioRepository;

@RestController                      // Indica que essa classe é um controller REST
@RequestMapping("/usuarios")          // Define o caminho base para os endpoints (ex: /usuarios)
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ---------- [GET] Listar todos ----------
    @GetMapping
    public List<UsuarioEntidade> listarTodos() {
        return usuarioRepository.findAll(); // Retorna todos os usuários do banco
    }

    // ---------- [GET] Buscar por ID ----------
    @GetMapping("/{id}")
    public UsuarioEntidade buscarPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElse(null); // Retorna o usuário, se existir
    }

    // ---------- [POST] Criar novo usuário ----------
    @PostMapping
    public UsuarioEntidade criarUsuario(@RequestBody UsuarioEntidade usuario) {
        return usuarioRepository.save(usuario); // Salva no banco e retorna o usuário criado
    }

    // ---------- [PUT] Atualizar usuário ----------
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

    // ---------- [DELETE] Excluir usuário ----------
    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }
}
