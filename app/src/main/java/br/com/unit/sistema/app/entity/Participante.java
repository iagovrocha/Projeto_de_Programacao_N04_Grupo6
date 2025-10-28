package br.com.unit.sistema.app.entity;

import br.com.unit.sistema.app.entity.Evento; 
import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name = "participantes")

public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;

    @ManyToMany
    @JoinTable(
            name = "evento_participantes",
            joinColumns = @JoinColumn(name = "participantes_id"),
            inverseJoinColumns = @JoinColumn(name = "evento_id")
    )

    private List<Evento> eventosInscritos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "tagsDePreferencia", joinColumns = @JoinColumn(name = "participante_id"))
    @Column(name = "tag")
    private List<String> tagsDePreferencia = new ArrayList<>();
    public void inscreverEmEvento(Evento evento) {
        eventosInscritos.add(evento);
    }

    public void cancelarInscricao(Evento evento) {
        eventosInscritos.remove(evento);
    }
}
