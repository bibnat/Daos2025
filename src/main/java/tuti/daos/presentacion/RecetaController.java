package tuti.daos.presentacion;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import tuti.daos.entidades.Receta;
import tuti.daos.servicios.RecetaService;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {

    private final RecetaService service;

    public RecetaController(RecetaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Receta> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public EntityModel<Receta> obtener(@PathVariable Integer id) {

        Receta r = service.buscarPorId(id);

        return EntityModel.of(
                r,

                // self
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(RecetaController.class).obtener(id)
                ).withSelfRel(),

                // listado de recetas
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(RecetaController.class).listar()
                ).withRel("recetas")
        );
    }

    @PostMapping
    public EntityModel<Receta> crear(@RequestBody Receta r) {

        Receta creada = service.crear(r);

        return EntityModel.of(
                creada,
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(RecetaController.class)
                                .obtener(creada.getId())
                ).withSelfRel()
        );
    }

    @PutMapping("/{id}")
    public EntityModel<Receta> actualizar(@PathVariable Integer id, @RequestBody Receta datos) {

        Receta actualizada = service.actualizar(id, datos);

        return EntityModel.of(
                actualizada,
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(RecetaController.class)
                                .obtener(actualizada.getId())
                ).withSelfRel()
        );
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
