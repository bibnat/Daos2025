package daos.tp.centroasistencias.entidades;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "asistencias")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "asistido_id", nullable = false)
    private Long asistidoId;

    @Column(name = "racion_id", nullable = false)
    private Long racionId;

    @Column(name = "fecha_entrega", nullable = false)
    private LocalDate fechaEntrega;

    public Asistencia() {}

    // GETTERS
    public Long getId() { return id; }
    public Long getAsistidoId() { return asistidoId; }
    public Long getRacionId() { return racionId; }
    public LocalDate getFechaEntrega() { return fechaEntrega; }

    // SETTERS
    public void setId(Long id) { this.id = id; }
    public void setAsistidoId(Long asistidoId) { this.asistidoId = asistidoId; }
    public void setRacionId(Long racionId) { this.racionId = racionId; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }
}
