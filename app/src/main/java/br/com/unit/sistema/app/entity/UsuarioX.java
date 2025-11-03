package br.com.unit.sistema.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class UsuarioX {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long usuaroId;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    public UsuarioX() {
    }

    public UsuarioX(Long usuaroId, String email, String senha) {
        this.usuaroId = usuaroId;
        this.email = email;
        this.senha = senha;
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
}
