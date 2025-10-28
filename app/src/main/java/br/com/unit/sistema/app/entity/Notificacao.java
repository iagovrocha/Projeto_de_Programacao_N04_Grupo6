package br.com.unit.sistema.app.entity;

import br.com.unit.sistema.app.controller.dto.NotificacaoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Notificacao")
@Table(name = "notificacao")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Notificacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idNotificacao;
    private long idRemetente;
    private String titulo;
    private String mensagem;
    private boolean status;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;
   
    public Notificacao(NotificacaoDTO dados) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.tipo = dados.tipo();
        this.status = true;
    }

}
