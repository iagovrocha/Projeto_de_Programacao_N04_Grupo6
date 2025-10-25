package br.com.unit.sistema.app.entity;



import br.com.unit.sistema.app.controller.dto.AtualizarTagsDTO;
import br.com.unit.sistema.app.controller.dto.CreateTagsDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "tags")
@Entity(name = "Tags")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idTag")
public class Tags {

    public Tags(CreateTagsDTO dados) {
        this.nomeTag = dados.nomeTag();
        this.corTag = dados.corTag();
        this.ativo = true;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tag")
    private Long idTag;

    @Column(name = "nome_tag")
    private String nomeTag;

    @Column(name = "cor_tag")
    private String corTag;

    @Column(name = "ativo")
    private Boolean ativo;


    public void inativar() {
        this.ativo = false;
    }

    public void atualizarInformacoes(AtualizarTagsDTO dados) {
       if (dados.nomeTag() != null) {
        this.nomeTag = dados.nomeTag();
       }
       if (dados.corTag() != null) {
        this.corTag = dados.corTag();
       }
       if (dados.ativo() != null) {
        this.ativo = dados.ativo();
       }
    }
}
