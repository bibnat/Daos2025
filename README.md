🌱 TP3 – Desarrollo de Aplicaciones Orientadas a Servicios (DAOS 2025)
Centro de Asistencias – Servicios S01 a S04

Repositorio: [https://github.com/bibnat/Daos2025](https://github.com/bibnat/Daos2025.git)


ARQUITECTURA INTERNA

El proyecto sigue estructura por capas:

```
src/
├── main/
│   ├── java/
│   │   └── tuti/daos/
│   │       ├── entidades/       # Clases Entity (Asistido, Asistencia, etc.)
│   │       └── accesoDatos/     # Repositories
│   └── resources/
│       ├── application.properties
│       └── data-carga.sql
```



    
    INSTRUCCIONES PARA EJECUTAR

* Clonar el repositorio
  git clone https://github.com/bibnat/Daos2025.git

* Configurar application.properties con tu base MySQL

* Ejecutar el proyecto con:
  mvn spring-boot:run

* Probar con Postman o cURL

---------------------------------------------------------
INTEGRANTES: 
* Casalaspro, Pablo
* Defranchi, Santiago
* Méndez, Guillermo
* Méndez, Mónica Natalia
