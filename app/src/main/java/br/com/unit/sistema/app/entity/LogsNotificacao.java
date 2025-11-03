package br.com.unit.sistema.app.entity;

import org.springframework.lang.Nullable;

import br.com.unit.sistema.app.controller.dto.CriarLogNotificacaoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "LogsNotificacao")
@Table(name = "logsnotificacao")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "idLogNotificacao")

public class LogsNotificacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long idLogNotificacao;

    private Long idNotificacao;
    private MethodTypeLog method;
    private String func_name;

    @Nullable
    @Column(name = "id_user")
    private Long idUser;

    public LogsNotificacao(CriarLogNotificacaoDTO dados){
        this.idNotificacao = dados.idNotificacao();
        this.method = dados.met();
        this.func_name = dados.func();
        this.idUser = dados.idUser(); 
    }
}
