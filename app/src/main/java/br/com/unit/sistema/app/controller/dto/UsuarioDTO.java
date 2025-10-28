package br.com.unit.sistema.app.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
}