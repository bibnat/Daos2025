package tuti.daos.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "asistido", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"dni"}),
        @UniqueConstraint(columnNames = {"nombreCompleto"})
})
public class Asistido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre completo es obligatorio")
    private String nombreCompleto;

    // DNI no es obligatorio, pero debe ser positivo si se ingresa
    @Positive(message = "El DNI debe ser positivo")
    private Integer dni;

    // Domicilio NO es obligatorio
    private String domicilio;

    // Fecha de nacimiento NO es obligatoria
    private LocalDate fechaNacimiento;

    @NotNull(message = "La edad al registrarse es obligatoria")
    @PositiveOrZero(message = "La edad no puede ser negativa")
    private Integer edadAlRegistrarse;

    // Campo obligatorio según el TP
    @ManyToOne
    @JoinColumn(name = "id_ciudad", nullable = false)
    private Ciudad ciudad;

    public Asistido() {}

    // Getters y setters

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public Integer getDni() { return dni; }
    public void setDni(Integer dni) { this.dni = dni; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public Integer getEdadAlRegistrarse() { return edadAlRegistrarse; }
    public void setEdadAlRegistrarse(Integer edadAlRegistrarse) { this.edadAlRegistrarse = edadAlRegistrarse; }

    public Ciudad getCiudad() { return ciudad; }
    public void setCiudad(Ciudad ciudad) { this.ciudad = ciudad; }
}
