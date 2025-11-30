package tuti.daos.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "receta", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nombre"})
})
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El peso de la ración es obligatorio")
    @Positive(message = "El peso debe ser un valor positivo")
    private Double pesoRacion;

    @NotNull(message = "Las calorías son obligatorias")
    @Positive(message = "Las calorías deben ser un número positivo")
    private Integer calorias;

    public Receta() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPesoRacion() {
        return pesoRacion;
    }

    public void setPesoRacion(Double pesoRacion) {
        this.pesoRacion = pesoRacion;
    }

    public Integer getCalorias() {
        return calorias;
    }

    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }
}
