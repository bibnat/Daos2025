package daos.tp.centroasistencias.entidades;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "asistidos")
public class Asistido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Integer dni;

    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    private String domicilio;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "edad_registro", nullable = false)
    private Integer edadRegistro;

    @ManyToOne
    @JoinColumn(name = "ciudad_id", nullable = false)
    private Ciudad ciudad;

    public Asistido() {}

    // -------- GETTERS --------

    public Long getId() {
        return id;
    }

    public Integer getDni() {
        return dni;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Integer getEdadRegistro() {
        return edadRegistro;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    // -------- SETTERS --------

    public void setId(Long id) {
        this.id = id;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setEdadRegistro(Integer edadRegistro) {
        this.edadRegistro = edadRegistro;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
}
