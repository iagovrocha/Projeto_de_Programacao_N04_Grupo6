package br.com.unit.sistema.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "eventos")

public class Evento {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String local;
    private String data;

    // Muitos eventos podem ser organizados por um organizador
    @ManyToOne
    @JoinColumn(name = "organizador_id")
    private Organizador organizador;

    // Um evento pode ter vários participantes e vice-versa
    @ManyToMany
    @JoinTable(
        name = "evento_participante",
        joinColumns = @JoinColumn(name = "evento_id"),
        inverseJoinColumns = @JoinColumn(name = "participante_id")
    )
    private List<Participante> participantes = new ArrayList<>();
}
