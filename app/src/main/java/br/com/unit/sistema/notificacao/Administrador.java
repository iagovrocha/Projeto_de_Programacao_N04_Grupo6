package br.com.unit.sistema.notificacao;

import java.util.ArrayList;

public class Administrador {
    private ArrayList<Pagamento> pagamentos;
    private ArrayList<Evento> eventos;
    private ArrayList<Notificacao> notificacoesCriadas;

    public Administrador() {
        this.pagamentos = new ArrayList<>();
        this.eventos = new ArrayList<>();
    }

    public ArrayList<Pagamento> getPagamentos() {
        return pagamentos;
    }
    public ArrayList<Evento> getEventos() {
        return eventos;
    }
    public ArrayList<Notificacao> getNotificacoesCriadas() {
        return notificacoesCriadas;
    }

    public void setPagamentos(ArrayList<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }
    public void setEventos(ArrayList<Evento> eventos) {
        this.eventos = eventos;
    }
    public void setNotificacoesCriadas(ArrayList<Notificacao> notificacoesCriadas) {
        this.notificacoesCriadas = notificacoesCriadas;
    }
    
    public void adicionarPagamento(Pagamento pagamento) {
        pagamentos.add(pagamento);
    }
    
    public void adicionarEvento(Evento evento) {
        eventos.add(evento);
    }

    public void enviarNotificacao(String mensagem, String titulo, Usuario destino) {
        Notificacao notificacao = new Notificacao(notificacoesCriadas.size()+1,titulo,mensagem,destino);
        notificacoesCriadas.add(notificacao);
    }

    public void atribuirTag(Notificacao notificacao, Tags tag) {
        notificacao.setTags(tag);
    }

}
