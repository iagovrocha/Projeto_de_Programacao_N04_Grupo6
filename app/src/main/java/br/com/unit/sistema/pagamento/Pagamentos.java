package br.com.unit.sistema.pagamento;

import java.util.Date;

public class Pagamentos {
    private Long idPagamento;
    private Double valor;
    private Date dataPagamento;
    private String status;
    private Long idParticipante;

    public Pagamentos(Long idPagamento, Double valor, Date dataPagamento, Long idParticipante) {
        this.idPagamento = idPagamento;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.idParticipante = idParticipante;
        this.status = "PENDENTE";
    }

    public Long getIdPagamento() {
        return idPagamento;
    }

    public Double getValor() {
        return valor;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public String getStatus() {
        return status;
    }

    public Long getIdParticipante() {
        return idParticipante;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    
}
