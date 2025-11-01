package br.com.unit.sistema.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "organizadores")

public class Organizador {

     // Um organizador pode organizar vários eventos
    @OneToMany(mappedBy = "organizador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evento> eventosOrganizados = new ArrayList<>();

    public void adicionarEvento(Evento evento) {
        eventosOrganizados.add(evento);
        evento.setOrganizador(this); // mantém consistência bidirecional
    }

    public void enviarNotificacao(String notificacao) {
        System.out.println("Notificação enviada: " + notificacao);
    }

    public void atribuirTag(String notificacao, String tag) {
        System.out.println("Tag atribuída: " + tag + " à notificação: " + notificacao);
    }
    
}
