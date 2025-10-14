package br.com.unit.sistema.tags;

public class Tags {
    private Long idTag;
    private String nome;
    
    public Tags(Long idTag, String nome) {
        this.idTag = idTag;
        this.nome = nome;
    }

    public Long getIdTag() {
        return idTag;
    }

    public void setIdTag(Long idTag) {
        this.idTag = idTag;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
