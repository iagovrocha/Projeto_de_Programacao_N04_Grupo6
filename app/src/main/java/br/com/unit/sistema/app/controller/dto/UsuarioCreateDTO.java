package br.com.unit.sistema.app.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioCreateDTO {
    private String senha;
    private String nome;
    private String email;
}