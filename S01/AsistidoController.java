package tuti.daos.presentacion;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import tuti.daos.entidades.Asistido;
import tuti.daos.presentacion.dto.AsistidoDTO;
import tuti.daos.servicios.AsistidoService;

import java.util.List;

@RestController
@RequestMapping("/api/asistidos")
public class AsistidoController {

    private final AsistidoService service;

    public AsistidoController(AsistidoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Asistido> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public EntityModel<Asistido> obtener(@PathVariable Integer id) {

        Asistido a = service.buscarPorId(id);

        return EntityModel.of(
                a,
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(AsistidoController.class).obtener(id)
                ).withSelfRel(),

                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(AsistidoController.class).listar()
                ).withRel("asistidos"),

                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(CiudadController.class)
                                .obtener(a.getCiudad().getId())
                ).withRel("ciudad"),

                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(AsistenciaController.class)
                                .listarPorAsistido(a.getId())
                ).withRel("asistencias")
        );
    }

    @PostMapping
    public EntityModel<Asistido> crear(@RequestBody AsistidoDTO dto) {

        Asistido creado = service.crearDesdeDTO(dto);

        return EntityModel.of(
                creado,
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(AsistidoController.class).obtener(creado.getId())
                ).withSelfRel()
        );
    }

    @PutMapping("/{id}")
    public EntityModel<Asistido> actualizar(@PathVariable Integer id, @RequestBody AsistidoDTO dto) {

        Asistido actualizado = service.actualizarDesdeDTO(id, dto);

        return EntityModel.of(
                actualizado,
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(AsistidoController.class).obtener(id)
                ).withSelfRel()
        );
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}

