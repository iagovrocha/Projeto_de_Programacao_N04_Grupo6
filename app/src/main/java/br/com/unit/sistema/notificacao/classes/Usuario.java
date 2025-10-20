package com.example.TesteNotificacoes.classes;

import java.util.List;

public abstract class Usuario {
    private Long id;
    private List<Notificacao> notificacoesRecebidas;

    public Usuario(Long id) {
        this.id = id;
    }

    public List<Notificacao> getNotificacoesRecebidas() {
        return notificacoesRecebidas;
    }

    public void setNotificacoesRecebidas(List<Notificacao> notificacoesRecebidas) {
        this.notificacoesRecebidas = notificacoesRecebidas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
