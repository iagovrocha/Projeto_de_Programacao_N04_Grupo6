package br.com.unit.sistema.app.services;

import br.com.unit.sistema.app.controller.dto.CriarNotificacaoDto;
import br.com.unit.sistema.app.controller.dto.EnviarNotificaoDto;
import br.com.unit.sistema.app.controller.dto.NotificacaoResponseDto;
import br.com.unit.sistema.app.entity.Notificacao;
import br.com.unit.sistema.app.entity.NotificacaoUsuario;
import br.com.unit.sistema.app.entity.NotificacaoUsuarioId;
import br.com.unit.sistema.app.entity.Usuario;
import br.com.unit.sistema.app.repository.NotificacaoRepository;
import br.com.unit.sistema.app.repository.NotificacaoUsuarioRepository;
import br.com.unit.sistema.app.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class NotificacaoService {
    private NotificacaoRepository notificacaoRepository;
    private UsuarioRepository usuarioRepository;
    private NotificacaoUsuarioRepository notificacaoUsuarioRepository;

    public NotificacaoService(NotificacaoRepository notificacaoRepository, UsuarioRepository usuarioRepository, NotificacaoUsuarioRepository notificacaoUsuarioRepository) {
        this.notificacaoRepository = notificacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.notificacaoUsuarioRepository = notificacaoUsuarioRepository;
    }

    public void enviarNotificacao(CriarNotificacaoDto criarNotificacaoDto) {

        Usuario remetente = usuarioRepository.findById(criarNotificacaoDto.remetenteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Usuario destinatario = usuarioRepository.findById(criarNotificacaoDto.destinatarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Notificacao notificacao = new Notificacao(
                null,
                criarNotificacaoDto.tipo(),
                criarNotificacaoDto.titulo(),
                criarNotificacaoDto.mensagem(),
                remetente
        );

        notificacaoRepository.save(notificacao);

    }

    public List<NotificacaoResponseDto> listarNotificacoesDeUsuario (Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return usuario.getNotificacoesRecebidas()
                .stream()
                .map(not -> new NotificacaoResponseDto(
                        not.getNotificacao().getNotificacaoId(),
                        not.getNotificacao().getTipo(),
                        not.getNotificacao().getTitulo(),
                        not.getNotificacao().getMensagem(),
                        not.getNotificacao().getRemetente().getUsuaroId()
                )).toList();
    }

    public void enviarNotificacoes(EnviarNotificaoDto enviarNotificaoDtos) {
        Usuario remetente = usuarioRepository.findById(enviarNotificaoDtos.remetenteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Notificacao notificacao = new Notificacao(
                null,
                enviarNotificaoDtos.tipo(),
                enviarNotificaoDtos.titulo(),
                enviarNotificaoDtos.mensagem(),
                remetente
        );

        notificacaoRepository.save(notificacao);

        for (Long usuarioId : enviarNotificaoDtos.idsDestinatarios()) {
            Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            NotificacaoUsuarioId id = new NotificacaoUsuarioId(notificacao.getNotificacaoId(), usuarioId);
            NotificacaoUsuario notificacaoUsuario = new NotificacaoUsuario(id, notificacao, usuario, false);
            notificacaoUsuarioRepository.save(notificacaoUsuario);
        }
    }
}