package br.com.unit.sistema.app.services;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unit.sistema.app.controller.dto.CriarLogNotificacaoDTO;
import br.com.unit.sistema.app.controller.dto.NotificacaoDTO;
import br.com.unit.sistema.app.controller.dto.NotificacaoDeletarDTO;
import br.com.unit.sistema.app.controller.dto.NotificacaoLidaDTO;
import br.com.unit.sistema.app.controller.dto.NotificacaoListagemDTO;
import br.com.unit.sistema.app.controller.dto.NotificacaoUsuarioListDTO;
import br.com.unit.sistema.app.entity.LogsNotificacao;
import br.com.unit.sistema.app.entity.MethodTypeLog;
import br.com.unit.sistema.app.entity.Notificacao;
import br.com.unit.sistema.app.entity.NotificacaoUsuario;
import br.com.unit.sistema.app.entity.NotificacaoUsuarioID;
import br.com.unit.sistema.app.entity.Pagamentos;
import br.com.unit.sistema.app.entity.Tipo;
import br.com.unit.sistema.app.repository.LogsNotificacaoRepository;
import br.com.unit.sistema.app.repository.NotificacaoRepository;
import br.com.unit.sistema.app.repository.NotificacaoUsuarioRepository;
import jakarta.validation.Valid;

@Service
public class NotificacaoService{
    
    @Autowired
    private NotificacaoRepository repository;

    @Autowired
    private NotificacaoUsuarioRepository notificacaoUsuarioRepository;

    @Autowired
    private LogsNotificacaoRepository logsNotificacaoRepository;
    
    public void gerarNotificacaoPagamento(Pagamentos dados,Tipo tipo){
        salvarNotificacao(new NotificacaoDTO(
        "Novo Pagamento Realizado", 
        "Pagamento: "+dados.getIdPagamento()+
        "Valor: "+dados.getValor()+
        "Data"+dados.getDataPagamento(),  
        tipo,
        null , 
        Arrays.asList(dados.getIdUsuario())));
    }

    public void gerarLogNotificacao(CriarLogNotificacaoDTO dados){

        logsNotificacaoRepository.save(new LogsNotificacao(dados ));

    }
    //GET
    //exibe todas as notificações registradas
    @Transactional(readOnly = true)
    public Page<NotificacaoListagemDTO> coletarNotificacao(@PageableDefault Pageable paginacao){
        //gerarLogNotificacao(new CriarLogNotificacaoDTO(null, null, MethodTypeLog.GET, "coletarNotificacao"));
        
        return repository.findAll(paginacao).map(notificacao -> new  NotificacaoListagemDTO(notificacao));
    }

    //exibe uma notificação específica
    @Transactional(readOnly = true)
    public NotificacaoListagemDTO exibirNotificacaoEspecifica(long id){
        //gerarLogNotificacao(new CriarLogNotificacaoDTO(id, null, MethodTypeLog.GET, "exibirNotificacaoEspecifica"));
        NotificacaoListagemDTO notificacao = new NotificacaoListagemDTO(repository.getReferenceById(id));

        return notificacao;
    }

    //exibir todas as notificacoes daquele usuario
    @Transactional(readOnly = true)
    public Page<NotificacaoUsuarioListDTO> coletarNotificacaoUsuario(long id, @PageableDefault Pageable paginacao){
        

        return notificacaoUsuarioRepository
        .findAllByIdUser(id, paginacao).map(notificacao -> new  NotificacaoUsuarioListDTO(repository.getReferenceById(notificacao.getIdNotificacaoUsuario().getIdNotificacao()), notificacao.isLida()));
    }

    //coleta notificações que um usuário enviou
    @Transactional(readOnly = true)
    public Page<NotificacaoListagemDTO> coletarNotificacaoEnviadas(long id, @PageableDefault Pageable paginacao){
        return repository.findByIdUser(id, paginacao).map(notificacao -> new NotificacaoListagemDTO(notificacao));
    }

    //filtra notificação pelo tipo
    @Transactional(readOnly = true)
    public Page<NotificacaoListagemDTO> filtrarNotificacaoTipo(Tipo tipo, @PageableDefault Pageable paginacao){
        return repository.findByTipo(tipo, paginacao).map(notificacao -> new NotificacaoListagemDTO(notificacao));
    }

    //POST
    //registra uma nova notificacao
    @Transactional
    public ResponseEntity<NotificacaoDTO> salvarNotificacao(@Valid NotificacaoDTO dados){
        Notificacao notificacao = new Notificacao(dados);
        repository.save(notificacao);
        gerarLogNotificacao(new CriarLogNotificacaoDTO(notificacao.getIdNotificacao(),notificacao.getIdUser(),MethodTypeLog.POST,"salvarNotificacao"));
        for(long id : dados.destinatarios()){
            notificacaoUsuarioRepository.save(new NotificacaoUsuario(id,notificacao.getIdNotificacao()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(dados);
    }

    //PUT
    //marca uma notificacao como lida
    @Transactional
    public ResponseEntity<NotificacaoLidaDTO> atualizarNotificacao(@Valid NotificacaoLidaDTO dados){
        try{
            NotificacaoUsuarioID id = new NotificacaoUsuarioID(dados);
            if (notificacaoUsuarioRepository.existsById(id) == false){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dados);
            }
            NotificacaoUsuario notificacao = notificacaoUsuarioRepository.getReferenceById(id);
            notificacao.marcarLida();
            gerarLogNotificacao(new CriarLogNotificacaoDTO(notificacao.getIdNotificacaoUsuario().getIdNotificacao(),notificacao.getIdNotificacaoUsuario().getIdUser(), MethodTypeLog.PUT, "atualizarNotificacao"));
            return ResponseEntity.ok(dados);
        }catch (Exception e ){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(dados);
        }
    }

    //DELETE
    //apaga uma notificacao especifica
    @Transactional
    public ResponseEntity<NotificacaoDeletarDTO> apagarNotificacao(NotificacaoDeletarDTO dados){
        notificacaoUsuarioRepository.deleteByIdNotificacao(dados.idNotificacao());
        repository.deleteById(dados.idNotificacao());
        gerarLogNotificacao(new CriarLogNotificacaoDTO(dados.idNotificacao(), null, MethodTypeLog.DELETE, "apagarNotificacao"));
        return ResponseEntity.notFound().build();
        }

}
