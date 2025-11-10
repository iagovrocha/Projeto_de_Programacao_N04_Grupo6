package br.com.unit.sistema.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unit.sistema.app.controller.dto.AtualizarUsuarioDTO;
import br.com.unit.sistema.app.controller.dto.UsuarioAutenticacaoDTO;
import br.com.unit.sistema.app.controller.dto.UsuarioCreateDTO;
import br.com.unit.sistema.app.controller.dto.UsuarioResponseDTO;
import br.com.unit.sistema.app.entity.UsuarioEntidade;
import br.com.unit.sistema.app.repository.UsuarioRepository;
import br.com.unit.sistema.app.services.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
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

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody @Valid UsuarioCreateDTO usuario) {
        return usuarioService.registrarUsuario(usuario);
    }

    @PostMapping("/login")
    public UsuarioResponseDTO fazerLogin(@RequestBody @Valid UsuarioAutenticacaoDTO dadosLogin){
        return usuarioService.autenticarUsuario(dadosLogin);
    }

    @PutMapping("/{id}")
    public UsuarioResponseDTO atualizarUsuario(@PathVariable Long id, @RequestBody @Valid AtualizarUsuarioDTO dados) {
        return usuarioService.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }
}
