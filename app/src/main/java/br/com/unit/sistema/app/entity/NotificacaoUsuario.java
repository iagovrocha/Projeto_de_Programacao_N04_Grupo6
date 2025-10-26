package br.com.unit.sistema.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "notificacao_usuario")
public class NotificacaoUsuario {

    @EmbeddedId
    private NotificacaoUsuarioId id;

    @JsonIgnore
    @ManyToOne
    @MapsId("notificacaoId")
    @JoinColumn(name = "notificacao_id")
    private Notificacao notificacao;

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "lida", nullable = false)
    private boolean lida;

    public NotificacaoUsuario() {
    }

    public NotificacaoUsuario(NotificacaoUsuarioId id, Notificacao notificacao, Usuario usuario, boolean lida) {
        this.id = id;
        this.notificacao = notificacao;
        this.usuario = usuario;
        this.lida = lida;
    }

    public NotificacaoUsuarioId getId() {
        return id;
    }

    public void setId(NotificacaoUsuarioId id) {
        this.id = id;
    }

    public Notificacao getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(Notificacao notificacao) {
        this.notificacao = notificacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }
}
