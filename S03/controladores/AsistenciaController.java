package tuti.daos.presentacion;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import tuti.daos.entidades.Asistencia;
import tuti.daos.entidades.Racion;
import tuti.daos.entidades.Asistido;
import tuti.daos.presentacion.dto.AsistenciaDTO;
import tuti.daos.servicios.AsistenciaService;

import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
public class AsistenciaController {

    private final AsistenciaService service;

    public AsistenciaController(AsistenciaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Asistencia> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public EntityModel<Asistencia> obtener(@PathVariable Integer id) {

        Asistencia a = service.buscarPorId(id);

        Asistido asistido = a.getAsistido();
        Racion racion = a.getRacion();

        return EntityModel.of(
                a,
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(AsistenciaController.class).obtener(id)
                ).withSelfRel(),
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(AsistidoController.class)
                                .obtener(asistido.getId())
                ).withRel("asistido"),
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(RacionController.class)
                                .obtener(racion.getId())
                ).withRel("racion"),
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(RecetaController.class)
                                .obtener(racion.getReceta().getId())
                ).withRel("receta"),
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(AsistenciaController.class).listar()
                ).withRel("asistencias")
        );
    }

    @GetMapping("/por-asistido/{idAsistido}")
    public List<Asistencia> listarPorAsistido(@PathVariable Integer idAsistido) {
        return service.listarPorAsistido(idAsistido);
    }

    @PostMapping
    public EntityModel<Asistencia> crear(@RequestBody AsistenciaDTO dto) {

        Asistencia creada = service.crearDesdeDTO(dto);

        return EntityModel.of(
                creada,
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(AsistenciaController.class)
                                .obtener(creada.getId())
                ).withSelfRel()
        );
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}

