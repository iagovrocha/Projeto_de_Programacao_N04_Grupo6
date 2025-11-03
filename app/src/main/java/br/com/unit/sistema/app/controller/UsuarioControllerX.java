package br.com.unit.sistema.app.controller;

import br.com.unit.sistema.app.controller.dto.CriarUsuarioDto;
import br.com.unit.sistema.app.entity.UsuarioX;
import br.com.unit.sistema.app.services.UsuarioServiceX;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControllerX {
    private UsuarioServiceX usuarioServiceX;

    public UsuarioControllerX(UsuarioServiceX usuarioServiceX) {
        this.usuarioServiceX = usuarioServiceX;
    }

    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody CriarUsuarioDto criarUsuarioDto) {
        usuarioServiceX.criarUsuario(criarUsuarioDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioX>> listarUsuarios() {
        var usuarios = usuarioServiceX.listarUsuarios();

        return ResponseEntity.ok(usuarios);
    }

}
