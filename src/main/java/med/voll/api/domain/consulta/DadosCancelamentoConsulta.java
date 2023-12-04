package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(
        @NotNull(message = "Id da consulta é obrigatório")
        Long id,
        @NotBlank(message = "Motivo do cancelamento é obrigatório")
        String motivoCancelamento) {
}
