package br.com.unit.sistema.app.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "NotificacaoUsuario")
@Table(name = "notificacaousuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class NotificacaoUsuario {
    @EmbeddedId
    private NotificacaoUsuarioID idNotificacaoUsuario;
    private boolean lida;

    public NotificacaoUsuario(long idUser, long idNotificacao){
        this.idNotificacaoUsuario = new NotificacaoUsuarioID(idUser,idNotificacao);
        this.lida = false;
    }

    public void marcarLida(){
        this.lida = true;
    }
    
}
