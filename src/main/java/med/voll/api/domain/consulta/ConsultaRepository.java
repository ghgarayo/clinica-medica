package med.voll.api.domain.consulta;

import io.micrometer.core.instrument.binder.db.MetricsDSLContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Query("select count(c) > 0 from Consulta c where c.medico.id = :idMedico and c.dataConsulta = :dataConsulta")
    boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime dataConsulta);

    @Query("select count(c) > 0 from Consulta c where c.paciente.id = :idPaciente and c.dataConsulta between :primeiroHorario and :ultimoHorario")
    boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

    Page<Consulta> findAllByAtivoTrue(Pageable pageable);

    Page<Consulta> findAllByAtivoFalse(Pageable pageable);
}
