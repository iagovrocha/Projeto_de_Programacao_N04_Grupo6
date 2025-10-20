package br.com.unit.sistema.notificacao;

import java.util.ArrayList;

public class Notificacao {
    private long id;
    private String titulo;
    private String mensagem;
    private boolean lida;
    /*  
     private Enum tipo;
     */
    private ArrayList<Usuario> destinatario;
    private Usuario remetente;  
    private ArrayList<Tags> tags;
    
    public Notificacao(long id, String titulo, String mensagem, Usuario destinatario) {
        this.id = id;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.lida = false;
        this.destinatario.add(destinatario);
    }

    public ArrayList<Usuario> getDestinatario() {
        return destinatario;
    }
    public void setDestinatario(ArrayList<Usuario> destinatario) {
        this.destinatario = destinatario;
    }
    public Usuario getRemetente() {
        return remetente;
    }
    public void setRemetente(Usuario remetente) {
        this.remetente = remetente;
    }

    public ArrayList<Tags> getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags.add(tags);
    }

    public long getId() {    
        return id;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getMensagem() {
        return mensagem;
    }
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    public boolean isLida() {
        return lida;
    }
    public void marcarComoLida() {
        this.lida = true;
    }
    



}
