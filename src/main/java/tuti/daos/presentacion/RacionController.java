package tuti.daos.presentacion;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import tuti.daos.entidades.Racion;
import tuti.daos.presentacion.dto.RacionDTO;
import tuti.daos.servicios.RacionService;

import java.util.List;

@RestController
@RequestMapping("/api/raciones")
public class RacionController {

    private final RacionService service;

    public RacionController(RacionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Racion> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public EntityModel<Racion> obtener(@PathVariable Integer id) {

        Racion r = service.buscarPorId(id);

        return EntityModel.of(
                r,
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(RacionController.class).obtener(id)
                ).withSelfRel(),
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(RacionController.class).listar()
                ).withRel("raciones"),
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(RecetaController.class)
                                .obtener(r.getReceta().getId())
                ).withRel("receta")
        );
    }

    @PostMapping
    public EntityModel<Racion> crear(@RequestBody RacionDTO dto) {

        Racion creada = service.crearDesdeDTO(dto);

        return EntityModel.of(
                creada,
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(RacionController.class).obtener(creada.getId())
                ).withSelfRel()
        );
    }

    @PutMapping("/{id}")
    public EntityModel<Racion> actualizar(@PathVariable Integer id, @RequestBody RacionDTO dto) {

        Racion actualizada = service.actualizarDesdeDTO(id, dto);

        return EntityModel.of(
                actualizada,
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(RacionController.class).obtener(id)
                ).withSelfRel()
        );
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}

