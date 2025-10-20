package com.projeto.semana1_projetoprog.Evento;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import com.projeto.semana1_projetoprog.Usuários.*;
import java.util.ArrayList;


@Data
@Entity
@Table(name = "eventos")

public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private String local;

    private Instant horario;

    @Temporal(TemporalType.DATE)
    private Date data;
    private String status;

    @ManyToOne
    @JoinColumn(name = "organizador_id")
    private Organizador organizador;

    @ManyToMany(mappedBy = "eventosInscritos")
    private List<Participante> participantes = new ArrayList<>();

}
