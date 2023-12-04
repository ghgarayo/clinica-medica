package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.ValidarAgendamentoDeConsulta;
import med.voll.api.domain.consulta.validacoes.ValidarHorarioDeCancelamento;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private List<ValidarAgendamentoDeConsulta> validadores;
    @Autowired
    private ValidarHorarioDeCancelamento validarCancelamento;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){

        if(!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Paciente não encontrado");
        }

        if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Médico não encontrado");
        }

        validadores.forEach(v -> v.validar(dados));

        var medico = escolherMedico(dados);
        if(medico == null){
            throw new ValidacaoException("Não há médicos disponíveis para a especialidade informada nesta data");
        }

        var paciente = pacienteRepository.findById(dados.idPaciente()).get();

        var consulta = new Consulta(medico, paciente, dados.dataConsulta());
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        if(dados.especialidade() == null){
            throw new ValidacaoException("Especialidade não informada");
        }
        System.out.println("nenhum médico informado, escolhendo aleatório");

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.dataConsulta());
    }

    public Page<DadosListagemConsultas> listar(Pageable pageable) {
        return consultaRepository.findAllByAtivoTrue(pageable).map(DadosListagemConsultas::new);
    }

    public void cancelar(DadosCancelamentoConsulta dados) {

        validarCancelamento.validar(dados);

        var consulta = consultaRepository.findById(dados.id()).orElseThrow(() -> new ValidacaoException("Consulta não encontrada"));
        consulta.inativar(dados);
        consultaRepository.save(consulta);

    }

    public Page<DadosListagemConsultas> listarCanceladas(Pageable pageable) {
        return consultaRepository.findAllByAtivoFalse(pageable).map(DadosListagemConsultas::new);
    }
}
