package br.com.unit.sistema.notificacao;
import java.util.List;

public class Usuário {
    private long id;
    private String nome;
    private String email;
    private String telefone;
    private String senha;
    private  List<Notificacao> notificacoesRecebidas;
    public String getEmail() {
        return email;
    }public long getId() {
        return id;
    }public String getNome() {
        return nome;
    }public List<Notificacao> getNotificacoesRecebidas() {
        return notificacoesRecebidas;
    }public String getSenha() {
        return senha;
    }public String getTelefone() {
        return telefone;
    }public void setEmail(String email) {
        this.email = email;
    }public void setId(long id) {
        this.id = id;
    }public void setNome(String nome) {
        this.nome = nome;
    }public void setNotificacoesRecebidas(List<Notificacao> notificacoesRecebidas) {
        this.notificacoesRecebidas = notificacoesRecebidas;
    }public void setSenha(String senha) {
        this.senha = senha;
    }public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void receberNotificacao(Notificacao notificacao){
        System.out.println(notificacao.getTitulo().toUpperCase());
        System.out.println(notificacao.getMensagem());
    }
}
