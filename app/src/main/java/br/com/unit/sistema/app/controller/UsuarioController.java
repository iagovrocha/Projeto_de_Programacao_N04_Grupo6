package br.com.unit.sistema.app.controller;

import br.com.unit.sistema.app.controller.dto.CriarUsuarioDto;
import br.com.unit.sistema.app.entity.Usuario;
import br.com.unit.sistema.app.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody CriarUsuarioDto criarUsuarioDto) {
        usuarioService.criarUsuario(criarUsuarioDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        var usuarios = usuarioService.listarUsuarios();

        return ResponseEntity.ok(usuarios);
    }

}
