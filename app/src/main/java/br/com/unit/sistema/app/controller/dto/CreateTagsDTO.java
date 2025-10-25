package br.com.unit.sistema.app.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateTagsDTO(
    @NotBlank
    String nomeTags,

    @NotBlank
    @Pattern(regexp = "^#([A-Fa-f0-9]{6})$")
    String corTags) {

}