package br.com.unit.sistema.notificacao;

import java.util.ArrayList;

public class Participante extends Usuario{
    private ArrayList<Evento> eventosInscritos;
    private ArrayList<Tag> tagsDePreferencia;
    private ArrayList<Pagamento> listaPagamentos;


    public Participante(Long id) {
        super(id);
    }

    public Participante(Long id, ArrayList<Evento> eventosInscritos, ArrayList<Tag> tagsDePreferencia, ArrayList<Pagamento> listaPagamentos) {
        super(id);
        this.eventosInscritos = eventosInscritos;
        this.tagsDePreferencia = tagsDePreferencia;
        this.listaPagamentos = listaPagamentos;
    }

    public void inscreverEmEvento (Evento evento) {
        evento.getParticipantes().add(this);
    }

    public void cancelarInscricao(Evento evento) {
        evento.getParticipantes().remove(this);
    }

    public void responderNotificacao (Notificacao notificacao, String resposta) {
        notificacao.setMensagem(notificacao.getMensagem() + "\nResposta:\n" + resposta);
    }

    public ArrayList<Evento> getEventosInscritos() {
        return eventosInscritos;
    }

    public void setEventosInscritos(ArrayList<Evento> eventosInscritos) {
        this.eventosInscritos = eventosInscritos;
    }

    public ArrayList<Tag> getTagsDePreferencia() {
        return tagsDePreferencia;
    }

    public void setTagsDePreferencia(ArrayList<Tag> tagsDePreferencia) {
        this.tagsDePreferencia = tagsDePreferencia;
    }

    public ArrayList<Pagamento> getListaPagamentos() {
        return listaPagamentos;
    }

    public void setListaPagamentos(ArrayList<Pagamento> listaPagamentos) {
        this.listaPagamentos = listaPagamentos;
    }
}
