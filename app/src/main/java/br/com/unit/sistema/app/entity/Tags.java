package br.com.unit.sistema.app.entity;



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
@EqualsAndHashCode(of = "idTags")
public class Tags {

    public Tags(CreateTagsDTO dados) {
        this.nomeTags = dados.nomeTags();
        this.corTags = dados.corTags();
        this.ativo = true;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tag")
    private Long idTags;

    @Column(name = "nome_tag")
    private String nomeTags;

    @Column(name = "cor_tag")
    private String corTags;

    @Column(name = "ativo")
    private Boolean ativo;
}
