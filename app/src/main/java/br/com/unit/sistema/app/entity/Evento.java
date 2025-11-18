package br.com.unit.sistema.app.entity;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.com.unit.sistema.app.controller.dto.EventoCreateDTO;
import br.com.unit.sistema.app.controller.dto.EventoUpdateDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Evento")
@Table(name = "eventos")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String nome;
    private String local;
    private String data;
    private Long idUser;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal preco;
    
    @ManyToMany
    @JoinTable(
        name = "usuario_evento",
        joinColumns = @JoinColumn(name = "evento_id"),
        inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private List<UsuarioEntidade> usuarios = new ArrayList<>();

    public Evento(EventoCreateDTO dto) {
        this.nome = dto.nome();
        this.local = dto.local();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        this.data = dto.data().format(formatter);
        
        this.idUser = dto.idOrganizador();
        this.preco = dto.preco() != null ? dto.preco() : BigDecimal.ZERO;
        this.usuarios = new ArrayList<>();
    }

    public void atualizar(EventoUpdateDTO dto) {
        this.nome = dto.nome();
        this.local = dto.local();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        this.data = dto.data().format(formatter);
    }
}
