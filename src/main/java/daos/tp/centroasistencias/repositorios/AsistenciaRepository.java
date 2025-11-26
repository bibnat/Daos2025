package daos.tp.centroasistencias.repositorios;

import daos.tp.centroasistencias.entidades.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    // Buscar todas las asistencias correspondientes a una ración específica
    List<Asistencia> findByRacionId(Long racionId);
}
