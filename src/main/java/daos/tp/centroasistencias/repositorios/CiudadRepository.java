package daos.tp.centroasistencias.repositorios;


import daos.tp.centroasistencias.entidades.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
}
