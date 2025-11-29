package tuti.daos.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "racion")
public class Racion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El stock preparado es obligatorio")
    @Positive(message = "El stock preparado debe ser un número positivo")
    private Integer stockPreparado;

    // NO se envía por POST/PUT → se calcula siempre en el servicio
    private Integer stockRestante;

    @NotNull(message = "La fecha de preparación es obligatoria")
    private LocalDate fechaPreparacion;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDate fechaVencimiento;

    @ManyToOne
    @JoinColumn(name = "id_receta", nullable = false)
    private Receta receta;

    public Racion() {}

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStockPreparado() {
        return stockPreparado;
    }

    public void setStockPreparado(Integer stockPreparado) {
        this.stockPreparado = stockPreparado;
    }

    public Integer getStockRestante() {
        return stockRestante;
    }

    public void setStockRestante(Integer stockRestante) {
        this.stockRestante = stockRestante;
    }

    public LocalDate getFechaPreparacion() {
        return fechaPreparacion;
    }

    public void setFechaPreparacion(LocalDate fechaPreparacion) {
        this.fechaPreparacion = fechaPreparacion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }
}

