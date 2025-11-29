package tuti.daos.servicios.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tuti.daos.entidades.Asistido;
import tuti.daos.entidades.Ciudad;
import tuti.daos.accesoDatos.AsistidoRepository;
import tuti.daos.accesoDatos.CiudadRepository;
import tuti.daos.excepciones.RecursoDuplicadoException;
import tuti.daos.excepciones.RecursoNoEncontradoException;
import tuti.daos.presentacion.dto.AsistidoDTO;
import tuti.daos.servicios.AsistidoService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AsistidoServiceImpl implements AsistidoService {

    private final AsistidoRepository repo;
    private final CiudadRepository ciudadRepo;

    public AsistidoServiceImpl(AsistidoRepository repo, CiudadRepository ciudadRepo) {
        this.repo = repo;
        this.ciudadRepo = ciudadRepo;
    }

    // ==========================================================
    // =============== CREAR DESDE DTO ===========================
    // ==========================================================
    @Override
    public Asistido crearDesdeDTO(AsistidoDTO dto) {

        if (dto.idCiudad == null)
            throw new IllegalArgumentException("La ciudad es obligatoria.");

        Ciudad ciudad = ciudadRepo.findById(dto.idCiudad)
                .orElseThrow(() -> new RecursoNoEncontradoException("La ciudad especificada no existe."));

        if (repo.existsByNombreCompleto(dto.nombreCompleto))
            throw new RecursoDuplicadoException("Nombre completo ya registrado.");

        if (dto.dni != null && repo.existsByDni(dto.dni))
            throw new RecursoDuplicadoException("DNI ya registrado.");

        Asistido a = new Asistido();
        a.setNombreCompleto(dto.nombreCompleto);
        a.setDni(dto.dni);
        a.setDomicilio(dto.domicilio);
        a.setEdadAlRegistrarse(dto.edadAlRegistrarse);

        if (dto.fechaNacimiento != null)
            a.setFechaNacimiento(LocalDate.parse(dto.fechaNacimiento));

        a.setCiudad(ciudad);

        return repo.save(a);
    }

    // ==========================================================
    // =============== ACTUALIZAR DESDE DTO ======================
    // ==========================================================
    @Override
    public Asistido actualizarDesdeDTO(Integer id, AsistidoDTO dto) {

        Asistido existente = buscarPorId(id);

        if (dto.idCiudad == null)
            throw new IllegalArgumentException("La ciudad es obligatoria.");

        Ciudad ciudad = ciudadRepo.findById(dto.idCiudad)
                .orElseThrow(() -> new RecursoNoEncontradoException("La ciudad especificada no existe."));

        if (!existente.getNombreCompleto().equals(dto.nombreCompleto)
                && repo.existsByNombreCompleto(dto.nombreCompleto))
            throw new RecursoDuplicadoException("Nombre completo ya registrado.");

        if (dto.dni != null &&
                !dto.dni.equals(existente.getDni()) &&
                repo.existsByDni(dto.dni))
            throw new RecursoDuplicadoException("DNI ya registrado.");

        existente.setNombreCompleto(dto.nombreCompleto);
        existente.setDni(dto.dni);
        existente.setDomicilio(dto.domicilio);
        existente.setEdadAlRegistrarse(dto.edadAlRegistrarse);

        if (dto.fechaNacimiento != null)
            existente.setFechaNacimiento(LocalDate.parse(dto.fechaNacimiento));

        existente.setCiudad(ciudad);

        return repo.save(existente);
    }

    // ==========================================================
    // =============== MÉTODOS BÁSICOS ===========================
    // ==========================================================
    @Override
    public Asistido buscarPorId(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Asistido no encontrado: " + id));
    }

    @Override
    public List<Asistido> listar() {
        return repo.findAll();
    }

    @Override
    public void eliminar(Integer id) {
        Asistido existente = buscarPorId(id);
        repo.delete(existente);
    }
}

