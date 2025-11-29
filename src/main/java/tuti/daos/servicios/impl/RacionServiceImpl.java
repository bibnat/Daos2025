package tuti.daos.servicios.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tuti.daos.accesoDatos.RacionRepository;
import tuti.daos.accesoDatos.RecetaRepository;
import tuti.daos.entidades.Racion;
import tuti.daos.entidades.Receta;
import tuti.daos.excepciones.RecursoNoEncontradoException;
import tuti.daos.excepciones.RecursoDuplicadoException;
import tuti.daos.presentacion.dto.RacionDTO;
import tuti.daos.servicios.RacionService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class RacionServiceImpl implements RacionService {

    private final RacionRepository repo;
    private final RecetaRepository recetaRepo;

    public RacionServiceImpl(RacionRepository repo, RecetaRepository recetaRepo) {
        this.repo = repo;
        this.recetaRepo = recetaRepo;
    }

    // ==========================================================
    // =============== CREAR DESDE DTO ===========================
    // ==========================================================
    @Override
    public Racion crearDesdeDTO(RacionDTO dto) {

        if (dto.idReceta == null)
            throw new IllegalArgumentException("Debe indicar una receta.");

        Receta receta = recetaRepo.findById(dto.idReceta)
                .orElseThrow(() -> new RecursoNoEncontradoException("La receta indicada no existe."));

        LocalDate prep = LocalDate.parse(dto.fechaPreparacion);
        LocalDate venc = LocalDate.parse(dto.fechaVencimiento);

        if (!venc.isAfter(prep))
            throw new RecursoDuplicadoException(
                    "La fecha de vencimiento debe ser posterior a la fecha de preparación."
            );

        Racion r = new Racion();
        r.setReceta(receta);
        r.setStockPreparado(dto.stockPreparado);
        r.setFechaPreparacion(prep);
        r.setFechaVencimiento(venc);

        // StockRestante inicia igual al stockPreparado
        r.setStockRestante(dto.stockPreparado);

        return repo.save(r);
    }

    // ==========================================================
    // =============== ACTUALIZAR DESDE DTO ======================
    // ==========================================================
    @Override
    public Racion actualizarDesdeDTO(Integer id, RacionDTO dto) {

        Racion existente = buscarPorId(id);

        if (dto.idReceta == null)
            throw new IllegalArgumentException("Debe indicar una receta.");

        Receta receta = recetaRepo.findById(dto.idReceta)
                .orElseThrow(() -> new RecursoNoEncontradoException("La receta indicada no existe."));

        LocalDate prep = LocalDate.parse(dto.fechaPreparacion);
        LocalDate venc = LocalDate.parse(dto.fechaVencimiento);

        if (!venc.isAfter(prep))
            throw new RecursoDuplicadoException(
                    "La fecha de vencimiento debe ser posterior a la fecha de preparación."
            );

        existente.setReceta(receta);
        existente.setStockPreparado(dto.stockPreparado);
        existente.setFechaPreparacion(prep);
        existente.setFechaVencimiento(venc);

        // EL STOCK RESTANTE NO SE TOCA
        existente.setStockRestante(existente.getStockRestante());

        return repo.save(existente);
    }

    // ==========================================================
    // =============== MÉTODOS BÁSICOS ===========================
    // ==========================================================
    @Override
    public Racion buscarPorId(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Ración no encontrada: " + id));
    }

    @Override
    public List<Racion> listar() {
        return repo.findAll();
    }

    @Override
    public void eliminar(Integer id) {
        Racion existente = buscarPorId(id);
        repo.delete(existente);
    }
}
