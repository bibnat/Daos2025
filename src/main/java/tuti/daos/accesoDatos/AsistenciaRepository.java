package tuti.daos.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tuti.daos.entidades.Asistencia;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer> {

    // Listar por asistido
    List<Asistencia> findByAsistido_Id(Integer idAsistido);

    // Validar asistencia duplicada en la misma fecha
    boolean existsByAsistido_IdAndFechaEntrega(Integer idAsistido, LocalDate fechaEntrega);
}
