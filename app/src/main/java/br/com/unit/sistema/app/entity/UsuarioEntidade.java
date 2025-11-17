package br.com.unit.sistema.app.entity;

import br.com.unit.sistema.app.controller.dto.AtualizarUsuarioDTO;
import br.com.unit.sistema.app.controller.dto.UsuarioCreateDTO;
import jakarta.persistence.Column;
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

@Entity(name = "Usuario")
@Table(name = "usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class UsuarioEntidade {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private long id;
    
    private String email;
    private String nome;
    private String senha;

    @Enumerated(EnumType.STRING)
    private Role role;

    public UsuarioEntidade(UsuarioCreateDTO dados) {
        this.email = dados.email();
        this.nome = dados.nome();
        this.role = dados.role();
        this.senha = dados.senha();
    }

    public void atualizarInfo(AtualizarUsuarioDTO dados) {
        if (dados.nome() != null && !dados.nome().isEmpty()) {
            this.nome = dados.nome();
        }
        if (dados.email() != null && !dados.email().isEmpty()) {
            this.email = dados.email();
        }
        if (dados.senha() != null && !dados.senha().isEmpty()) {
            this.senha = dados.senha();
        }
        if (dados.role() != null) {
            this.role = dados.role();
        }
    }
}