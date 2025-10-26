package br.com.unit.sistema.app.controller;

import br.com.unit.sistema.app.controller.dto.CriarNotificacaoDto;
import br.com.unit.sistema.app.controller.dto.EnviarNotificaoDto;
import br.com.unit.sistema.app.controller.dto.NotificacaoResponseDto;
import br.com.unit.sistema.app.services.NotificacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {
    private NotificacaoService notificacaoService;

    public NotificacaoController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @PostMapping
    public ResponseEntity<?> enviarNotificacao(@RequestBody CriarNotificacaoDto criarNotificacaoDto) {
        notificacaoService.enviarNotificacao(criarNotificacaoDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/teste")
    public ResponseEntity<?> enviarNotificacoes (@RequestBody EnviarNotificaoDto enviarNotificaoDto) {
        notificacaoService.enviarNotificacoes(enviarNotificaoDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<NotificacaoResponseDto>> listarNotificacoesDeUsuario (@PathVariable("usuarioId") Long usuarioId) {
        var notificacoes = notificacaoService.listarNotificacoesDeUsuario(usuarioId);

        return ResponseEntity.ok(notificacoes);
    }

}
