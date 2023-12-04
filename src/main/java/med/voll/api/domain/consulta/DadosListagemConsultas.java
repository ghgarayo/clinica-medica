package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Especialidade;

public record DadosListagemConsultas(Long id,
                                     String nomePaciente,
                                     String nomeMedico,
                                     Especialidade especialidade,
                                     String dataConsulta,
                                     boolean ativo,
                                     String motivoCancelamento) {

    public DadosListagemConsultas(Consulta consulta) {
        this(consulta.getId(),
                consulta.getPaciente().getNome(),
                consulta.getMedico().getNome(),
                consulta.getMedico().getEspecialidade(),
                consulta.getDataConsulta().toString(),
                consulta.isAtivo(),
                consulta.getMotivoCancelamento());
    }


}
