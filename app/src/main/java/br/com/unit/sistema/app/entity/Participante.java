package br.com.unit.sistema.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "participantes")

public class Participante {
    
    @ManyToMany(mappedBy = "participantes")
    private List<Evento> eventosParticipados = new ArrayList<>();

}
