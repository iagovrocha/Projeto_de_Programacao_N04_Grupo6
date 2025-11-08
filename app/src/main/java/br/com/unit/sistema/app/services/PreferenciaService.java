package br.com.unit.sistema.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.Repositories;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unit.sistema.app.controller.dto.atualizarPreferenciaDTO;
import br.com.unit.sistema.app.controller.dto.criarPreferenciaDTO;
import br.com.unit.sistema.app.controller.dto.deletarPreferenciaDTO;
import br.com.unit.sistema.app.controller.dto.listarPreferenciaDTO;
import br.com.unit.sistema.app.entity.Preferencia;
import br.com.unit.sistema.app.repository.PreferenciaRepository;
import br.com.unit.sistema.app.repository.UsuarioRepository;
import jakarta.validation.Valid;

@Service
public class PreferenciaService {
    
    @Autowired
    private PreferenciaRepository prefRepo;

    @Autowired
    private UsuarioRepository userRepository;

    @Transactional(readOnly = true)
    public ResponseEntity listarPref(@PageableDefault Pageable paginacao){
        return ResponseEntity.ok().body(prefRepo.findAll(paginacao).map(preferencia -> new listarPreferenciaDTO(preferencia)));
    }

    @Transactional(readOnly = true)
    public Page<Preferencia> listarPrefUser(long idUser, Pageable paginacao){
        return prefRepo.findAllByIdUser(idUser, paginacao);
    }

    @Transactional
    public ResponseEntity cadastrarPref(@Valid criarPreferenciaDTO dados){
        if (userRepository.existsById(dados.idUser()) == false){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dados.idUser());
            }
        Preferencia pref = new Preferencia(dados);
        prefRepo.save(pref);

        return ResponseEntity.status(HttpStatus.CREATED).body(dados);
    }

    @Transactional
    public ResponseEntity atualizarPref(@Valid atualizarPreferenciaDTO dados){
            if (prefRepo.existsById(dados.idPreferencia()) == false){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dados.idPreferencia());
            }

            Preferencia pref = prefRepo.getReferenceById(dados.idPreferencia());
            pref.definirTipo(dados.tipo());

        return ResponseEntity.ok().body(dados);
    }

    @Transactional
    public ResponseEntity deletarPref(@Valid deletarPreferenciaDTO dados){
        if (prefRepo.existsById(dados.idPreferencia()) == false){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dados.idPreferencia());
         }
        prefRepo.deleteById(dados.idPreferencia());

        return ResponseEntity.ok().body(dados);
    }
}
