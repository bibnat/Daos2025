package daos.tp.centroasistencias.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "recetas")
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nombre;

    // peso de la ración en, por ejemplo, gramos
    @Positive
    @Column(name = "peso_racion", nullable = false)
    private Double pesoRacion;

    // calorías por ración
    @Positive
    @Column(name = "calorias_racion", nullable = false)
    private Integer caloriasRacion;

    public Receta() {}

    // GETTERS
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Double getPesoRacion() { return pesoRacion; }
    public Integer getCaloriasRacion() { return caloriasRacion; }

    // SETTERS
    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPesoRacion(Double pesoRacion) { this.pesoRacion = pesoRacion; }
    public void setCaloriasRacion(Integer caloriasRacion) { this.caloriasRacion = caloriasRacion; }
}
