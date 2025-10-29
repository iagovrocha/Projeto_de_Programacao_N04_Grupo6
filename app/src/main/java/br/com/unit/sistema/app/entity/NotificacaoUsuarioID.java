package br.com.unit.sistema.app.entity;

import java.io.Serializable;

import br.com.unit.sistema.app.controller.dto.NotificacaoLidaDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class NotificacaoUsuarioID implements Serializable{
    
    @Column(name = "idUser")
    private long idUser;

    @Column(name = "idNotificacao")
    private long idNotificacao;

    public NotificacaoUsuarioID(NotificacaoLidaDTO dados){
        this.idUser = dados.idUser();
        this.idNotificacao = dados.idNotificacao();
    }

}
