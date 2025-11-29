package tuti.daos.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import tuti.daos.entidades.Ciudad;

public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {
}