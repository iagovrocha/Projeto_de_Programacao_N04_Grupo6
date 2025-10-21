package br.com.unit.sistema.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {

    @GetMapping
    public String listarNotificacao(){
        return "<h1>Notificação</h1>";
    }

    @PostMapping
    public String criarNotificacao(){
        return "mensagem";
    }
    
}
