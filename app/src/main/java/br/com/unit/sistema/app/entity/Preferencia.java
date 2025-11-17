package br.com.unit.sistema.app.entity;

import br.com.unit.sistema.app.controller.dto.criarPreferenciaDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Preferencia")
@Table(name = "preferencia")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "idPreferencia")
public class Preferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPreferencia;

    private long idUser;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;


    public Preferencia(criarPreferenciaDTO dados){
        this.idUser = dados.idUser();
        this.tipo = dados.tipo();
    }
    

    public void definirTipo(Tipo tipo){
        this.tipo = tipo;
    }
}
