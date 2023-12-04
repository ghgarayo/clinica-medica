package med.voll.api.controller;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.medico.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> dadosDetalhementoMedicoJson;

    @MockBean
    private MedicoRepository repository;

    @Test
    @DisplayName("Deve retornar codigo 201 ao cadastrar um medico com informacoes validas")
    @WithMockUser
    void cadastrar() throws Exception {
        var dadosCadastroMedico = new DadosCadastroMedico(
                "Carla Azevedo",
                "carla.azevedo@voll.med",
                "4199321-2201",
                "321451",
                Especialidade.CARDIOLOGIA,
                new DadosEndereco(
                    "rua21",
                    "bairro",
                    "12345678",
                    "Brasilia",
                    "DF",
                    "1",
                    "complemento"
                )
        );

        var medico = new Medico(dadosCadastroMedico);

        when(repository.save(any(Medico.class))).thenReturn(medico);

        var response = mvc.perform(post("/medicos")
                .contentType("application/json")
                .content("""
                            {
                            "nome": "Carla Azevedo",
                            "email": "carla.azevedo@voll.med.com",
                            "crm": "321451",
                            "especialidade": "CARDIOLOGIA",
                            "telefone": "4199321-2201",
                            "endereco": {
                                "logradouro": "rua21",
                                "bairro": "bairro",
                                "cep": "12345678",
                                "cidade": "Brasilia",
                                "uf": "DF",
                                "numero": "1",
                                "complemento": "complemento"
                                }
                            }
                        """)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }
}