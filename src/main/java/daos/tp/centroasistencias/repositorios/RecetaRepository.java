package daos.tp.centroasistencias.repositorios;

import daos.tp.centroasistencias.entidades.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaRepository extends JpaRepository<Receta, Long> {

}
