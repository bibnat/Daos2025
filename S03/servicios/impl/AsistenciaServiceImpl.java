package tuti.daos.servicios.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuti.daos.accesoDatos.AsistenciaRepository;
import tuti.daos.accesoDatos.AsistidoRepository;
import tuti.daos.accesoDatos.RacionRepository;
import tuti.daos.entidades.Asistencia;
import tuti.daos.entidades.Asistido;
import tuti.daos.entidades.Racion;
import tuti.daos.excepciones.RecursoNoEncontradoException;
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

    public AsistenciaServiceImpl(AsistenciaRepository repo,
                                 AsistidoRepository asistidoRepo,
                                 RacionRepository racionRepo) {
        this.repo = repo;
        this.asistidoRepo = asistidoRepo;
        this.racionRepo = racionRepo;
    }

    @Override
    public List<Asistencia> listar() {
        return repo.findAll();
    }

    @Override
    public Asistencia buscarPorId(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe asistencia con id " + id));
    }

    @Override
    public List<Asistencia> listarPorAsistido(Integer idAsistido) {
        return repo.findByAsistido_Id(idAsistido);
    }

    @Override
    public Asistencia crearDesdeDTO(AsistenciaDTO dto) {

        Asistido asistido = asistidoRepo.findById(dto.idAsistido)
                .orElseThrow(() -> new RecursoNoEncontradoException("Asistido no encontrado"));

        Racion racion = racionRepo.findById(dto.idRacion)
                .orElseThrow(() -> new RecursoNoEncontradoException("Ración no encontrada"));

        Asistencia asistencia = new Asistencia();
        asistencia.setAsistido(asistido);
        asistencia.setRacion(racion);
        asistencia.setFechaEntrega(LocalDate.now());

        return repo.save(asistencia);
    }

    @Override
    public void eliminar(Integer id) {
        Asistencia asistencia = buscarPorId(id);
        repo.delete(asistencia);
    }
}
