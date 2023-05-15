package med.voll.apiv2.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import med.voll.apiv2.domain.usuario.Usuario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

    public String gerarToken(Usuario usuario) { 
        try {
            var algoritmo = Algorithm.HMAC256("12345678");
            return JWT.create()
                .withIssuer("API Voll.med")
                .withSubject(usuario.getLogin())
                .withExpiresAt(dataExpiracao())
                .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerrar token jwt", exception);
        }        
    }

    private Instant dataExpiracao() {
        /*
           * Método de expiração do token JWT, pega o instante de criação do token e
           * adiciona 2 horas, utilizando o método plusHours(). Por ser um "Instant",
           * é necessário setar a timezone, utilizando o método ZoneOffset(),
           * que recebe uma String com o fuso horário para GMT"
           */
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}








