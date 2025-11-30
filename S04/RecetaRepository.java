package tuti.daos.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import tuti.daos.entidades.Receta;

public interface RecetaRepository extends JpaRepository<Receta, Integer> {

    boolean existsByNombre(String nombre);
}