package daos.tp.centroasistencias.servicios;

import daos.tp.centroasistencias.dto.RacionDTO;
import daos.tp.centroasistencias.dto.RecetaDTO;
import daos.tp.centroasistencias.dto.RecetaRequestDTO;
import daos.tp.centroasistencias.entidades.Receta;
import daos.tp.centroasistencias.repositorios.RacionRepository;
import daos.tp.centroasistencias.repositorios.RecetaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecetaService {

    private final RecetaRepository repo;

    @Autowired
    private RacionRepository racionRepository;

    public RecetaService(RecetaRepository repo) {
        this.repo = repo;
    }

    // --------- CREAR ---------
    public RecetaDTO crear(RecetaRequestDTO dto) {

        validar(dto);

        Receta r = new Receta();
        r.setNombre(dto.nombre);
        r.setPesoRacion(dto.pesoRacion);
        r.setCaloriasRacion(dto.caloriasRacion);

        repo.save(r);

        return agregarLinks(new RecetaDTO(r));
    }

    // --------- ACTUALIZAR ---------
    public RecetaDTO actualizar(Long id, RecetaRequestDTO dto) {

        Receta r = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        validar(dto);

        r.setNombre(dto.nombre);
        r.setPesoRacion(dto.pesoRacion);
        r.setCaloriasRacion(dto.caloriasRacion);

        repo.save(r);

        return agregarLinks(new RecetaDTO(r));
    }

    // --------- LISTAR ---------
    public List<RecetaDTO> listar() {
        return repo.findAll()
                .stream()
                .map(r -> agregarLinks(new RecetaDTO(r)))
                .toList();
    }

    // --------- BUSCAR ---------
    public RecetaDTO buscar(Long id) {
        Receta r = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));
        return agregarLinks(new RecetaDTO(r));
    }

    // --------- ELIMINAR ---------
    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    // --------- VALIDAR ---------
    private void validar(RecetaRequestDTO dto) {

        if (dto.nombre == null || dto.nombre.isBlank())
            throw new RuntimeException("El nombre de la receta es obligatorio");

        if (dto.pesoRacion == null || dto.pesoRacion <= 0)
            throw new RuntimeException("El peso de la ración debe ser positivo");

        if (dto.caloriasRacion == null || dto.caloriasRacion <= 0)
            throw new RuntimeException("Las calorías deben ser positivas");
    }

    // --------- HATEOAS ---------
    private RecetaDTO agregarLinks(RecetaDTO dto) {

        dto.add(Link.of("/api/recetas/" + dto.id).withSelfRel());
        dto.add(Link.of("/api/recetas/" + dto.id + "/raciones").withRel("raciones"));

        return dto;
    }

    // --------- RACIONES DE UNA RECETA ---------
    public List<RacionDTO> listarRacionesPorReceta(Long recetaId) {

        repo.findById(recetaId)
            .orElseThrow(() -> new RuntimeException("La receta no existe"));

        return racionRepository.findByRecetaId(recetaId)
                .stream()
                .map(RacionDTO::new)
                .toList();
    }
}
