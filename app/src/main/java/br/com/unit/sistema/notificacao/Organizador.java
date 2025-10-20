package br.com.unit.sistema.notificacao;

import java.util.ArrayList;
import java.util.List;

public class Organizador extends Usuario{
    private ArrayList<Evento> eventosOrganizados;

    public Organizador(Long id) {
        super(id);
    }

    public void adicionarEvento (Evento evento) {
        eventosOrganizados.add(evento);
    }

    public void enviarNotificacao (Notificacao notificacao, List<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            usuario.getNotificacoesRecebidas().add(notificacao);
        }
    }

    public void atribuirTag(Notificacao notificacao, Tag tag) {
        notificacao.getTags().add(tag);
    }

    public ArrayList<Evento> getEventosOrganizados() {
        return eventosOrganizados;
    }
}
