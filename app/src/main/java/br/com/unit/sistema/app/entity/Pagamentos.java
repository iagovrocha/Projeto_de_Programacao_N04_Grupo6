package br.com.unit.sistema.app.entity;

import java.time.Instant;

import br.com.unit.sistema.app.controller.dto.PagamentoCreateDTO;
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

@Table(name = "pagamentos")
@Entity(name = "Pagamentos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idPagamento")
public class Pagamentos {
    
    public Pagamentos(PagamentoCreateDTO dados) {
        this.idUsuario = dados.idUsuario();
        this.valor = dados.valor();
        this.status = true;

    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento")
    private Long idPagamento;

    @Column(name = "id_usuario")
    private Long idUsuario;
    
    @Column(name = "valor")
    private Double valor;

    @Column(name = "data_pagamento", insertable = false, updatable = false)
    private Instant dataPagamento;

    @Column(name = "criado_em", insertable = false, updatable = false)
    private Instant criadoEm;
    
    @Column(name = "status")
    private Boolean status;

    

}
