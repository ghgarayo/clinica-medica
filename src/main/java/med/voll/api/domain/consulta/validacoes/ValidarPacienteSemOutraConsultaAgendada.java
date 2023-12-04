package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarPacienteSemOutraConsultaAgendada implements ValidarAgendamentoDeConsulta{

    @Autowired
    public ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        var primeiroHorario = dados.dataConsulta().withHour(7);
        var ultimoHorario = dados.dataConsulta().withHour(18);

        var pacientePossuiConsultaAgendada = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);

        if(pacientePossuiConsultaAgendada){
            throw new ValidacaoException("O paciente já possui uma consulta agendada para este dia");
        }

    }
}
