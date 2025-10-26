package br.com.unit.sistema.app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notificacao")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificacao_id")
    private Long notificacaoId;

    @Column(name = "tipo")
    private TipoNotificacao tipo;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "mensagem")
    private String mensagem;

    @ManyToOne
    @JoinColumn(name = "remetente_id")
    private Usuario remetente;

    public Notificacao() {
    }

    public Notificacao(Long notificacaoId, TipoNotificacao tipo, String titulo, String mensagem, Usuario remetente) {
        this.notificacaoId = notificacaoId;
        this.tipo = tipo;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.remetente = remetente;
    }

    public Long getNotificacaoId() {
        return notificacaoId;
    }

    public void setNotificacaoId(Long notificacaoId) {
        this.notificacaoId = notificacaoId;
    }

    public TipoNotificacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoNotificacao tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }


    public Usuario getRemetente() {
        return remetente;
    }

    public void setRemetente(Usuario remetente) {
        this.remetente = remetente;
    }
}