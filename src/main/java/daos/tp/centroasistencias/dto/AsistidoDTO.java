package daos.tp.centroasistencias.dto;

import daos.tp.centroasistencias.entidades.Asistido;

public class AsistidoDTO {

    private Long id;
    private Integer dni;
    private String nombreCompleto;
    private String domicilio;
    private Integer edadRegistro;

    public AsistidoDTO(Asistido a) {
        this.id = a.getId();
        this.dni = a.getDni();
        this.nombreCompleto = a.getNombreCompleto();
        this.domicilio = a.getDomicilio();
        this.edadRegistro = a.getEdadRegistro();
    }

    public Long getId() { return id; }
    public Integer getDni() { return dni; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getDomicilio() { return domicilio; }
    public Integer getEdadRegistro() { return edadRegistro; }
}
