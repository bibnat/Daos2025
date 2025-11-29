package tuti.daos.accesoDatos;

import org.springframework.data.jpa.repository.JpaRepository;
import tuti.daos.entidades.Racion;

public interface RacionRepository extends JpaRepository<Racion, Integer> {
}