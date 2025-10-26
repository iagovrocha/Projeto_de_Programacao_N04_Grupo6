package br.com.unit.sistema.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long usuaroId;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<NotificacaoUsuario> notificacoesRecebidas;

    public Usuario() {
    }

    public Usuario(Long usuaroId, String email, String senha, List<NotificacaoUsuario> notificacoesRecebidas) {
        this.usuaroId = usuaroId;
        this.email = email;
        this.senha = senha;
        this.notificacoesRecebidas = notificacoesRecebidas;
    }

    public Long getUsuaroId() {
        return usuaroId;
    }

    public void setUsuaroId(Long usuaroId) {
        this.usuaroId = usuaroId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<NotificacaoUsuario> getNotificacoesRecebidas() {
        return notificacoesRecebidas;
    }

    public void setNotificacoesRecebidas(List<NotificacaoUsuario> notificacoesRecebidas) {
        this.notificacoesRecebidas = notificacoesRecebidas;
    }
}
