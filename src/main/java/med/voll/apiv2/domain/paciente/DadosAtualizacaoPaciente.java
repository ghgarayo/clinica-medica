package med.voll.apiv2.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.apiv2.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(
        @NotNull Long id,
        String nome,
        String telefone,
        String email,
        DadosEndereco endereco) {

}
