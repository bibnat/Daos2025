package daos.tp.centroasistencias.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import daos.tp.centroasistencias.entidades.Asistido;

public interface AsistidoRepository extends JpaRepository<Asistido, Long> {

    Optional<Asistido> findByNombreCompleto(String nombreCompleto);

    Optional<Asistido> findByDni(Integer dni);
}
