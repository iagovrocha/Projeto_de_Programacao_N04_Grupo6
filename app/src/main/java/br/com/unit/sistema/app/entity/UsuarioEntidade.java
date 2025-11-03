package br.com.unit.sistema.app.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class UsuarioEntidade {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String email;
    private String nome;
    private String senha;
    private Role role;
}