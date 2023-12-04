package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {
    @Autowired
    private AgendaDeConsultas agendamentoService;

    @PostMapping
    public ResponseEntity agendar (@RequestBody DadosAgendamentoConsulta dados){
        var dadosConsulta = agendamentoService.agendar(dados);
        return ResponseEntity.ok(dadosConsulta);
    }

    @GetMapping
    public ResponseEntity listar (@PageableDefault(size = 10, sort = "dataConsulta") Pageable pageable){
        var consultas = agendamentoService.listar(pageable);
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/canceladas")
    public ResponseEntity listarCanceladas(@PageableDefault(size = 10, sort = "dataConsulta") Pageable pageable){
        var consultas = agendamentoService.listarCanceladas(pageable);
        return ResponseEntity.ok(consultas);
    }

    @DeleteMapping
    public ResponseEntity cancelar (@RequestBody DadosCancelamentoConsulta dados){
        agendamentoService.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
