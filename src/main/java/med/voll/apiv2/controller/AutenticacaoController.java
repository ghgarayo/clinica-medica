package med.voll.apiv2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.apiv2.domain.usuario.DadosAutenticacao;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    //classe do proprio Spring que dispara o processo de autenticacao

    @Autowired
    private AuthenticationManager manager;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DadosAutenticacao dados){
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(token);


        return ResponseEntity.ok().build();

    }

    
}