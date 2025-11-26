package daos.tp.centroasistencias.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "raciones")
public class Racion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock_preparado", nullable = false)
    @Positive(message = "El stock preparado debe ser un número positivo")
    private Integer stockPreparado;

    @Column(name = "stock_restante", nullable = false)
    private Integer stockRestante;

    @Column(name = "receta_id", nullable = false)
    private Long recetaId;

    @Column(name = "fecha_preparacion", nullable = false)
    private LocalDate fechaPreparacion;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    // --------------- CONSTRUCTOR ---------------
    public Racion() {}

    // --------------- GETTERS -------------------
    public Long getId() { return id; }
    public Integer getStockPreparado() { return stockPreparado; }
    public Integer getStockRestante() { return stockRestante; }
    public Long getRecetaId() { return recetaId; }
    public LocalDate getFechaPreparacion() { return fechaPreparacion; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }

    // --------------- SETTERS -------------------
    public void setId(Long id) { this.id = id; }
    public void setStockPreparado(Integer stockPreparado) { this.stockPreparado = stockPreparado; }
    public void setStockRestante(Integer stockRestante) { this.stockRestante = stockRestante; }
    public void setRecetaId(Long recetaId) { this.recetaId = recetaId; }
    public void setFechaPreparacion(LocalDate fechaPreparacion) { this.fechaPreparacion = fechaPreparacion; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

}
