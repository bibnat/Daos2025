package tuti.daos.entidades;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "asistencia")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Relación obligatoria con Asistido
    @ManyToOne
    @JoinColumn(name = "id_asistido", nullable = false)
    private Asistido asistido;

    // Relación obligatoria con Racion
    @ManyToOne
    @JoinColumn(name = "id_racion", nullable = false)
    private Racion racion;

    // Se genera automáticamente en el Service
    private LocalDate fechaEntrega;

    public Asistencia() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Asistido getAsistido() { return asistido; }
    public void setAsistido(Asistido asistido) { this.asistido = asistido; }

    public Racion getRacion() { return racion; }
    public void setRacion(Racion racion) { this.racion = racion; }

    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }
}
