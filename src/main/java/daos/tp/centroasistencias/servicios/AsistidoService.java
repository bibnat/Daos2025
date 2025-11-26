package daos.tp.centroasistencias.servicios;

import daos.tp.centroasistencias.dto.AsistidoDTO;
import daos.tp.centroasistencias.dto.AsistidoRequestDTO;
import daos.tp.centroasistencias.entidades.Asistido;
import daos.tp.centroasistencias.entidades.Ciudad;
import daos.tp.centroasistencias.repositorios.AsistidoRepository;
import daos.tp.centroasistencias.repositorios.CiudadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsistidoService {

    private final AsistidoRepository asistidoRepository;
    private final CiudadRepository ciudadRepository;

    public AsistidoService(AsistidoRepository asistidoRepository,
                           CiudadRepository ciudadRepository) {
        this.asistidoRepository = asistidoRepository;
        this.ciudadRepository = ciudadRepository;
    }

    // ----------------- CREAR -----------------
    public AsistidoDTO guardar(AsistidoRequestDTO dto) {

        // Validar ciudad
        Ciudad ciudad = ciudadRepository.findById(dto.ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        // Validar nombre único
        asistidoRepository.findByNombreCompleto(dto.nombreCompleto).ifPresent(a -> {
            throw new RuntimeException("El nombre ya está registrado");
        });

        // Validar DNI si viene cargado
        if (dto.dni != null) {
            asistidoRepository.findByDni(dto.dni).ifPresent(a -> {
                throw new RuntimeException("El DNI ya está registrado");
            });
        }

        Asistido a = new Asistido();
        a.setDni(dto.dni);
        a.setNombreCompleto(dto.nombreCompleto);
        a.setDomicilio(dto.domicilio);
        a.setFechaNacimiento(dto.fechaNacimiento);
        a.setEdadRegistro(dto.edadRegistro);
        a.setCiudad(ciudad);

        asistidoRepository.save(a);
        return new AsistidoDTO(a);
    }

    // ----------------- LISTAR -----------------
    public List<AsistidoDTO> listar() {
        return asistidoRepository.findAll()
                .stream()
                .map(AsistidoDTO::new)
                .toList();
    }

    // ----------------- BUSCAR POR ID -----------------
    public AsistidoDTO buscarPorId(Long id) {
        Asistido a = asistidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asistido no encontrado"));
        return new AsistidoDTO(a);
    }

    // ----------------- ACTUALIZAR -----------------
    public AsistidoDTO actualizar(Long id, AsistidoRequestDTO dto) {

        Asistido a = asistidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asistido no encontrado"));

        // Validar ciudad
        Ciudad ciudad = ciudadRepository.findById(dto.ciudadId)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada"));

        a.setDni(dto.dni);
        a.setNombreCompleto(dto.nombreCompleto);
        a.setDomicilio(dto.domicilio);
        a.setFechaNacimiento(dto.fechaNacimiento);
        a.setEdadRegistro(dto.edadRegistro);
        a.setCiudad(ciudad);

        asistidoRepository.save(a);
        return new AsistidoDTO(a);
    }

    // ----------------- ELIMINAR -----------------
    public void eliminar(Long id) {
        if (!asistidoRepository.existsById(id)) {
            throw new RuntimeException("Asistido no encontrado");
        }
        asistidoRepository.deleteById(id);
    }
}
