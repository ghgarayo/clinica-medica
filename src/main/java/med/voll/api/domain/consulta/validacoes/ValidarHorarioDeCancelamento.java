package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidarHorarioDeCancelamento implements ValidarCancelamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosCancelamentoConsulta dados ){
        var consulta = repository.findById(dados.id()).get();
        var now = LocalDateTime.now();
        var diferencaEmHoras = Duration.between(now, consulta.getDataConsulta()).toHours();

        if(diferencaEmHoras < 24){
            throw new ValidacaoException("A consulta só pode ser cancelada com no mínimo 24 horas de antecedência");
        }

    }

}
