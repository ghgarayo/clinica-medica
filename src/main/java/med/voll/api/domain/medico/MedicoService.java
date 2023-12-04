package med.voll.api.domain.medico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository repository;

    public DadosDetalhamentoMedico cadastrar(DadosCadastroMedico dados){
        Medico medico = new Medico(dados);
        repository.save(medico);

        return new DadosDetalhamentoMedico(medico);
    }

}
