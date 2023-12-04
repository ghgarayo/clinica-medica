package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidarHorarioFuncionamentoClinica implements ValidarAgendamentoDeConsulta{
    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.dataConsulta();
        var isSunday = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var beforeOpeningTime = dataConsulta.getHour() < 7;
        var afterClosingTime = dataConsulta.getHour() > 18;

        if (isSunday || beforeOpeningTime || afterClosingTime) {
            throw new ValidacaoException("Horário de funcionamento da clínica: Segunda a Sábado das 07:00 às 18:00");
        }

    }

}

