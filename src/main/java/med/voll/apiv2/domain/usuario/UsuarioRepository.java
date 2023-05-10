package med.voll.apiv2.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /*
     * Método responsável por fazer a consulta no banco de dados.
     */
    UserDetails findByLogin(String login);

}
