package br.com.unit.sistema.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unit.sistema.app.controller.dto.NotificacaoDTO;
import br.com.unit.sistema.app.controller.dto.PagamentoCreateDTO;
import br.com.unit.sistema.app.controller.dto.PagamentosListagemDTO;
import br.com.unit.sistema.app.entity.Pagamentos;
import br.com.unit.sistema.app.entity.Tipo;
import br.com.unit.sistema.app.repository.PagamentosRepositorys;

@Service
public class PagamentosService {

    @Autowired
    PagamentosRepositorys repositorys;

    @Autowired
    private NotificacaoService notificacaoService;

    @Transactional
    public void create(PagamentoCreateDTO dados) {
        Pagamentos pagamento = repositorys.save(new Pagamentos(dados));
        notificacaoService.salvarNotificacao(new NotificacaoDTO(pagamento,Tipo.AVISO));
    }

    @Transactional(readOnly = true)
    public Page<PagamentosListagemDTO> listAllPagamentos(Pageable paginacao) {
        return repositorys.findAll(paginacao)
            .map(PagamentosListagemDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<PagamentosListagemDTO> listPagamentosByUsuario(Long idUsuario, Pageable paginacao) {
        return repositorys.findByIdUsuario(idUsuario, paginacao)
            .map(PagamentosListagemDTO::new);
    }

    // @Transactional
    // public void pagar(Long id) {
    //     Pagamentos pagamento = repositorys.findById(id)
    //         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pagamento não encontrado"));
    //     pagamento.pagar();
    // }
}
