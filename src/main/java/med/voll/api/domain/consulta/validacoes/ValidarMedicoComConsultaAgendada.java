package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarMedicoComConsultaAgendada implements ValidarAgendamentoDeConsulta{
    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        var medicoPossuiConsultaAgendada = repository.existsByMedicoIdAndData(dados.idMedico(), dados.dataConsulta());

        if(medicoPossuiConsultaAgendada){
            throw new ValidacaoException("O médico já possui uma consulta agendada para este dia");
        }

    }

}
