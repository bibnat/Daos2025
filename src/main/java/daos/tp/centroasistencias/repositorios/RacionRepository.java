package daos.tp.centroasistencias.repositorios;

import daos.tp.centroasistencias.entidades.Racion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RacionRepository extends JpaRepository<Racion, Long> {
	
	List<Racion> findByRecetaId(Long recetaId);


}


