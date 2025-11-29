package tuti.daos.servicios.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tuti.daos.accesoDatos.*;
import tuti.daos.entidades.*;
import tuti.daos.excepciones.*;
import tuti.daos.presentacion.dto.AsistenciaDTO;
import tuti.daos.servicios.AsistenciaService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AsistenciaServiceImpl implements AsistenciaService {

    private final AsistenciaRepository repo;
    private final AsistidoRepository asistidoRepo;
    private final RacionRepository racionRepo;

    public AsistenciaServiceImpl(
            AsistenciaRepository repo,
            AsistidoRepository asistidoRepo,
            RacionRepository racionRepo
    ) {
        this.repo = repo;
        this.asistidoRepo = asistidoRepo;
        this.racionRepo = racionRepo;
    }

    // ================================
    //        CREAR DESDE DTO
    // ================================
    @Override
    public Asistencia crearDesdeDTO(AsistenciaDTO dto) {

        if (dto.idAsistido == null)
            throw new IllegalArgumentException("Debe indicar un asistido.");

        if (dto.idRacion == null)
            throw new IllegalArgumentException("Debe indicar una ración.");

        Asistido asistido = asistidoRepo.findById(dto.idAsistido)
                .orElseThrow(() -> new RecursoNoEncontradoException("El asistido indicado no existe."));

        Racion racion = racionRepo.findById(dto.idRacion)
                .orElseThrow(() -> new RecursoNoEncontradoException("La ración indicada no existe."));

        // Stock disponible
        if (racion.getStockRestante() <= 0)
            throw new RecursoDuplicadoException("La ración no tiene stock disponible.");

        LocalDate hoy = LocalDate.now();

        // No entregar ración vencida
        if (hoy.isAfter(racion.getFechaVencimiento()))
            throw new RecursoDuplicadoException("La ración está vencida. No se puede entregar.");

        // Validación: una asistencia por día por asistido
        if (repo.existsByAsistido_IdAndFechaEntrega(dto.idAsistido, hoy))
            throw new RecursoDuplicadoException("El asistido ya recibió una asistencia hoy.");

        // Crear asistencia
        Asistencia asistencia = new Asistencia();
        asistencia.setAsistido(asistido);
        asistencia.setRacion(racion);
        asistencia.setFechaEntrega(hoy);

        // Descontar stock
        racion.setStockRestante(racion.getStockRestante() - 1);
        racionRepo.save(racion);

        return repo.save(asistencia);
    }

    @Override
    public Asistencia buscarPorId(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Asistencia no encontrada: " + id));
    }

    @Override
    public List<Asistencia> listar() {
        return repo.findAll();
    }

    @Override
    public List<Asistencia> listarPorAsistido(Integer idAsistido) {
        return repo.findByAsistido_Id(idAsistido);
    }

    @Override
    public void eliminar(Integer id) {
        Asistencia asistencia = buscarPorId(id);

        Racion racion = asistencia.getRacion();

        // Devolver stock
        racion.setStockRestante(racion.getStockRestante() + 1);
        racionRepo.save(racion);

        repo.delete(asistencia);
    }
}
