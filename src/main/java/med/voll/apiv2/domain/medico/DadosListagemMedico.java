package med.voll.apiv2.domain.medico;

public record DadosListagemMedico(Long id, String nome, String email, String crm, Especialidade especialidade) {

    /*
     * recebe um objeto do tipo médico e chama o construtor do record passando os
     * apenas atributos necessários para o retorno de dados para listagem
     */
    
    public DadosListagemMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }

}