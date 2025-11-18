package br.com.unit.sistema.app.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unit.sistema.app.controller.dto.InscricaoDTO;
import br.com.unit.sistema.app.controller.dto.InscricaoResponseDTO;
import br.com.unit.sistema.app.controller.dto.NotificacaoDTO;
import br.com.unit.sistema.app.controller.dto.PagamentoCreateDTO;
import br.com.unit.sistema.app.entity.Evento;
import br.com.unit.sistema.app.entity.Tipo;
import br.com.unit.sistema.app.entity.UsuarioEntidade;
import br.com.unit.sistema.app.repository.EventoRepository;
import br.com.unit.sistema.app.repository.UsuarioRepository;

@Service
public class InscricaoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PagamentosService pagamentosService;

    @Autowired
    private NotificacaoService notificacaoService;

    @Transactional
    public InscricaoResponseDTO inscreverUsuarioEmEvento(InscricaoDTO dto) {
        UsuarioEntidade usuario = usuarioRepository.findById(dto.idUsuario())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Evento evento = eventoRepository.findById(dto.idEvento())
            .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        if (evento.getUsuarios().contains(usuario)) {
            throw new RuntimeException("Usuário já está inscrito neste evento");
        }

        evento.getUsuarios().add(usuario);
        eventoRepository.save(evento);

        BigDecimal valorEvento = evento.getPreco() != null ? evento.getPreco() : BigDecimal.ZERO;
        Double valorDouble = valorEvento.doubleValue();
        
        PagamentoCreateDTO pagamentoDTO = new PagamentoCreateDTO(usuario.getId(), valorDouble);
        pagamentosService.create(pagamentoDTO);

        NotificacaoDTO notificacaoDTO = new NotificacaoDTO(evento, usuario.getId(), Tipo.CONFIRMACAO);
        notificacaoService.salvarNotificacao(notificacaoDTO);

        return new InscricaoResponseDTO(
            null,
            usuario.getId(),
            usuario.getNome(),
            evento.getId(),
            evento.getNome(),
            valorEvento,
            "CONFIRMADO",
            "Inscrição realizada com sucesso!"
        );
    }
}
