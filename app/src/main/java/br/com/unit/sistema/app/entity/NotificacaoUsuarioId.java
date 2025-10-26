package br.com.unit.sistema.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class NotificacaoUsuarioId {

    @Column(name = "notificacao_id")
    private Long notificacaoId;

    @Column(name = "notificacao_id")
    private Long usuarioId;

    public NotificacaoUsuarioId() {
    }

    public NotificacaoUsuarioId(Long notificacaoId, Long usuarioId) {
        this.notificacaoId = notificacaoId;
        this.usuarioId = usuarioId;
    }

    public Long getNotificacaoId() {
        return notificacaoId;
    }

    public void setNotificacaoId(Long notificacaoId) {
        this.notificacaoId = notificacaoId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
