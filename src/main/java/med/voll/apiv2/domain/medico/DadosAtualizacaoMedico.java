package med.voll.apiv2.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.apiv2.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(

        @NotNull Long id,
        String nome, 
        String telefone, 
        DadosEndereco endereco) {

}
