package br.com.unit.sistema.app.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.projeto.semana1_projetoprog.Usuarios.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
    private LocalDateTime dataHora;
    private String status;

    @ManyToOne
    @JoinColumn(name = "organizador_id")
    private Organizador organizador;

    @ManyToMany(mappedBy = "eventosInscritos")
    private List<Participante> participantes = new ArrayList<>();

}
