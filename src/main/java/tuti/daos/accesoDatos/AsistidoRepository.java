package tuti.daos.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import tuti.daos.entidades.Asistido;

public interface AsistidoRepository extends JpaRepository<Asistido, Integer> {

    boolean existsByDni(Integer dni);
    boolean existsByNombreCompleto(String nombreCompleto);
}