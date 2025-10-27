package com.projeto.semana1_projetoprog.Usuarios;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import com.projeto.semana1_projetoprog.Evento.*;

@Data
@Entity
@Table(name = "organizadores")
public class Organizador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;

    // Um organizador pode organizar vários eventos
    @OneToMany(mappedBy = "organizador", cascade = CascadeType.ALL)
    private List<Evento> eventosOrganizados = new ArrayList<>();


    public void adicionarEvento(Evento evento) {
        eventosOrganizados.add(evento);
    }

    public List<Evento> getEventosOrganizados() {
        return eventosOrganizados;
    }

    public void setEventosOrganizados(List<Evento> eventos) {
        this.eventosOrganizados = eventos;
    }


    public void enviarNotificacao(String notificacao) {
        System.out.println("Notificação enviada: " + notificacao);
    }

    public void atribuirTag(String notificacao, String tag) {
        System.out.println("Tag atribuída: " + tag + " à notificação: " + notificacao);
    }
}
