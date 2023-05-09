package med.voll.apiv2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.apiv2.domain.medico.*;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> cadastrar(@RequestBody @Valid DadosCadastroMedico dados,
            UriComponentsBuilder uriBuilder) {
        /*
         * Código 201 devolve no corpo da resposta:
         * os dados do novo recurso/registro criado
         * cabeçalho protocolo HTTP (Location)
         * A URI criada representa o endereço e cabe ao Spring criar o cabeçalho
         * location
         * path() recebe o complemento da URL.
         * buildAndExpand() recebe o id recem criado no banco de dados.
         * toUri() cria o objeto URI.
         * body() a informação que será devolvida no corpo da resposta, neste caso o
         * DTO.
         * 
         */

        var medico = new Medico(dados);
        repository.save(medico);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(
            @PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
        /*
         * Para paginação, passa-se um Pageable e o retorno será uma Page. FindAll agora
         * tem uma sobrecarga que recebe a paginacao como atributo, e o Page já possui o
         * .map() como metodo.
         * Nao é necessário usar o toList()
         * 
         * USANDO PAGINAÇÃO E ORDENAÇÃO NA URL DA API
         * 
         * -=-=-= Paginação =-=-=-
         * /medicos?size=[x]&page=[y]
         * onde X controla o numero de registro por página e Y controla qual página está
         * sendo acessada
         * 
         * -=-=-= Ordenação =-=-=-
         * /medicos?sort=[z],[j]
         * onde Z é o atributo que será utilizado para ordenar a lista e J define se é
         * ascendente (asc) ou descendente (desc)
         */

        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoMedico> atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity inativar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.inativar();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoMedico> detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}