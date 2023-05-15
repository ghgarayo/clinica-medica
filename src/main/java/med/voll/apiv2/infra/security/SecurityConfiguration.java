package med.voll.apiv2.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /*
     * SecurityFilterChain é o tipo objeto do Spring que será utilizado para
     * configuracoes processos de autenticacao e autorizacao.
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*
         * Recebe o atributo http do tipo
         * HttpSecurity que irá habilitar diferentes configurações e também
         * o método build() para criação do objeto SecurityFilterChain que será retornado.
         */
        return http
                .csrf().disable() // desabilita a proteção contra ataques CSRF do Spring
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Objeto com atributo estático STATELESS 
                .build(); // Cria o objeto do tipo SecurityFilterChain
    }

    /*
     * o @Bean validation serve para exportar uma classe para o Spring,
     * fazendo com que ele consiga carregá-lo e realize sua injeção de
     * dependencia em outras classes
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        /*
         *  Esta classe cria o objeto AuthenticantionManager. 
         */
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        /*
         * Algoritmo de hashing de senhas para nao salvar em texto limpo no banco de dados,
         * assim evitando possiveis vazamentos.
         */
        return new BCryptPasswordEncoder();
    }
}
