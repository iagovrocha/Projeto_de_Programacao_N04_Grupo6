package br.com.unit.sistema.app.services;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import br.com.unit.sistema.app.entity.Preferencia;
import br.com.unit.sistema.app.entity.Role;
import br.com.unit.sistema.app.entity.Tipo;
import br.com.unit.sistema.app.entity.UsuarioEntidade;
import br.com.unit.sistema.app.repository.LogsNotificacaoRepository;
import br.com.unit.sistema.app.repository.NotificacaoRepository;
import br.com.unit.sistema.app.repository.NotificacaoUsuarioRepository;
import br.com.unit.sistema.app.repository.TagsRepositorys;
import br.com.unit.sistema.app.repository.UsuarioRepository;
import jakarta.validation.Valid;
@Service
public class NotificacaoService{
    
    @Autowired
    private NotificacaoRepository repository;

    @Autowired
    private NotificacaoUsuarioRepository notificacaoUsuarioRepository;

    @Autowired
    private LogsNotificacaoRepository logsNotificacaoRepository;

    @Autowired
    private TagsRepositorys tagsRepositorys;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private JavaMailSender senderEmail;

    @Autowired
    private PreferenciaService prefServ;
    
    public void gerarLogNotificacao(CriarLogNotificacaoDTO dados){

        logsNotificacaoRepository.save(new LogsNotificacao(dados ));

    }

    public void gerarEmail(String destino, String assunto, String corpo){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("grupo06.n04.pp@gmail.com");
        email.setTo(destino);
        email.setSubject(assunto);
        email.setText(corpo);

        senderEmail.send(email);
        System.out.println("O email foi enviado para o usuário");
    }


    //GET
    //exibe todas as notificações registradas
    @Transactional(readOnly = true)
    public ResponseEntity coletarNotificacao(long id,@PageableDefault Pageable paginacao){
        if(!userRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        
        if(userRepository.getReferenceById(id).getRole() != Role.ADMINISTRADOR){
                return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(repository.findAll(paginacao).map(notificacao -> new  NotificacaoListagemDTO(notificacao)));
    }

    //exibe uma notificação específica
    @Transactional(readOnly = true)
    public ResponseEntity exibirNotificacaoEspecifica(long id){
        if (repository.existsById(id) == false){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id);
        }
        NotificacaoListagemDTO notificacao = new NotificacaoListagemDTO(repository.getReferenceById(id));

        return ResponseEntity.ok(notificacao);
    }

    //exibir todas as notificacoes daquele usuario
    @Transactional(readOnly = true)
    public ResponseEntity coletarNotificacaoUsuario(long id, @PageableDefault Pageable paginacao){
        if (userRepository.existsById(id) == false){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id);
        }
        return ResponseEntity.ok(notificacaoUsuarioRepository
        .findAllByIdUser(id, paginacao).map(notificacao -> 
            new  NotificacaoUsuarioListDTO(repository.getReferenceById(notificacao.getIdNotificacaoUsuario().getIdNotificacao()),
            notificacao.isLida())));
    }

    //Exibe notificacoes novas do usuario com preferencia
    public Page<NotificacaoUsuario> gerarPageNotificacaoUsuario(ArrayList<NotificacaoUsuario> lista, Pageable paginacao){
        int comeco = (int) paginacao.getOffset();
        int fim = Math.min(comeco   + paginacao.getPageSize(), lista.size());

        List<NotificacaoUsuario> subLista = lista.subList(comeco, fim);


        return new PageImpl<>(subLista,paginacao, lista.size());
    }

    public ArrayList<NotificacaoUsuario> ordenarListaNotificacaoUsuario(long id,ArrayList<Long> lista, Pageable paginacao){
        ArrayList<NotificacaoUsuario> listaOrdenada = new ArrayList<>();
        lista.sort(Comparator.naturalOrder());
        List<Preferencia> listaPref = prefServ.listarPrefUser(id, paginacao).getContent();
        int count = 0;
        if (listaPref.isEmpty() == true){
            for (Long idNot : lista){
                listaOrdenada.add(0,notificacaoUsuarioRepository.getReferencedByIdUN(id, idNot));
            }
            return listaOrdenada;
        }
        for (Long idNot : lista){
            Notificacao not = repository.getReferenceById(idNot);
            for (Preferencia pref : listaPref){
                if (pref.getTipo().equals(not.getTipo())){
                    listaOrdenada.add(0,notificacaoUsuarioRepository.getReferencedByIdUN(id, idNot));
                    count++;
                }else{
                    listaOrdenada.add(count,notificacaoUsuarioRepository.getReferencedByIdUN(id, idNot));
                }
            }
        }
        return listaOrdenada;
    }

    @Transactional(readOnly = true)
    public ResponseEntity exibirNotificacaoNLida(long id, @PageableDefault Pageable paginacao){
        ArrayList<NotificacaoUsuario> lista = ordenarListaNotificacaoUsuario(id, notificacaoUsuarioRepository.findNewByIdUser(id), paginacao);
        
        Page<NotificacaoUsuario> notUserPage = gerarPageNotificacaoUsuario(lista,paginacao);
        return ResponseEntity.ok().body(notUserPage.map(notificacao -> 
            new  NotificacaoUsuarioListDTO(repository.getReferenceById(notificacao.getIdNotificacaoUsuario().getIdNotificacao()),
            notificacao.isLida())));
    }

    //coleta notificações que um usuário enviou
    @Transactional(readOnly = true)
    public ResponseEntity coletarNotificacaoEnviadas(long id, @PageableDefault Pageable paginacao){
        if (userRepository.existsById(id) == false){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id);
        }
        if(userRepository.getReferenceById(id).getRole() != Role.ADMINISTRADOR){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repository.findByIdUser(id, paginacao).map(notificacao -> new NotificacaoListagemDTO(notificacao)));
    }

    //filtra notificação pelo tipo
    @Transactional(readOnly = true)
    public Page<NotificacaoListagemDTO> filtrarNotificacaoTipo(Tipo tipo, @PageableDefault Pageable paginacao){
        return repository.findByTipo(tipo, paginacao).map(notificacao -> new NotificacaoListagemDTO(notificacao));
    }

    //POST
    //registra uma nova notificacao
    @Transactional
    public ResponseEntity salvarNotificacao(@Valid NotificacaoDTO dados){
       try{ 
            if (dados.idRemetente() != null && userRepository.existsById(dados.idRemetente()) == false){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dados.idRemetente());
            }
            if (dados.idRemetente() != null && userRepository.getReferenceById(dados.idRemetente()).getRole() != Role.ADMINISTRADOR){
                return ResponseEntity.badRequest().build();
            }
            Notificacao notificacao = new Notificacao(dados);
            repository.save(notificacao);
            gerarLogNotificacao(new CriarLogNotificacaoDTO(notificacao.getIdNotificacao(),notificacao.getIdUser(),MethodTypeLog.POST,"salvarNotificacao"));
            for(long id : dados.destinatarios()){
                if (userRepository.existsById(id) == true){
                    notificacaoUsuarioRepository.save(new NotificacaoUsuario(id,notificacao.getIdNotificacao()));
                    UsuarioEntidade user = userRepository.getReferenceById(id);
                    System.out.println("ENVIO DE EMAIL \n\n\n");
                    gerarEmail(user.getEmail(), "Nova Notificacao Para Você "+user.getNome()+"- ID: "+notificacao.getIdNotificacao(), notificacao.getMensagem());
                    System.out.println("\n\n\nEMAIL ENVIADO");
                }
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(dados);
        }catch (Exception e ){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
       }
    }

    //PUT
    //marca uma notificacao como lida
    @Transactional
    public ResponseEntity atualizarNotificacao(@Valid NotificacaoLidaDTO dados){
        try{
            NotificacaoUsuarioID id = new NotificacaoUsuarioID(dados);
            if (notificacaoUsuarioRepository.existsById(id) == false){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id);
            }
            NotificacaoUsuario notificacao = notificacaoUsuarioRepository.getReferenceById(id);
            notificacao.marcarLida();
            gerarLogNotificacao(new CriarLogNotificacaoDTO(notificacao.getIdNotificacaoUsuario().getIdNotificacao(),notificacao.getIdNotificacaoUsuario().getIdUser(), MethodTypeLog.PUT, "atualizarNotificacao"));
            return ResponseEntity.ok(dados);
        }catch (Exception e ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //atualiza tag de uma notificacao, tanto para adição tanto para troca
    @Transactional
    public ResponseEntity atualizarTag(@Valid NotificacaoLidaDTO dados){
        try{
            if(repository.existsById(dados.idNotificacao()) == false){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dados.idNotificacao());
            }
            Notificacao notificacao = repository.getReferenceById(dados.idNotificacao());
            if (tagsRepositorys.existsById(dados.idTag()) == false){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dados.idTag());
            }
            if(userRepository.getReferenceById(dados.idUser()).getRole() != Role.ADMINISTRADOR){
                return ResponseEntity.badRequest().build();
            }
            notificacao.definirTag(dados.idTag());
            return ResponseEntity.ok(dados);}
        catch(Exception e ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //DELETE
    //apaga uma notificacao especifica
    @Transactional
    public ResponseEntity apagarNotificacao(NotificacaoDeletarDTO dados){
        try{
            if(repository.existsById(dados.idNotificacao()) == false){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dados.idNotificacao());
            }
            notificacaoUsuarioRepository.deleteByIdNotificacao(dados.idNotificacao());
            repository.deleteById(dados.idNotificacao());
            gerarLogNotificacao(new CriarLogNotificacaoDTO(dados.idNotificacao(), null, MethodTypeLog.DELETE, "apagarNotificacao"));
            return ResponseEntity.ok(dados);
        }catch(Exception e ){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
