package tuti.daos.excepciones;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Recurso no encontrado
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<MensajeError> manejarNoEncontrado(
            RecursoNoEncontradoException ex,
            HttpServletRequest request) {

        MensajeError error = new MensajeError(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 409 - Conflictos o duplicados
    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<MensajeError> manejarDuplicado(
            RecursoDuplicadoException ex,
            HttpServletRequest request) {

        MensajeError error = new MensajeError(
                ex.getMessage(),
                HttpStatus.CONFLICT.value(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // 400 - Errores generales de validación
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MensajeError> manejarArgumentosInvalidos(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        MensajeError error = new MensajeError(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 400 - Cualquier otro RuntimeException no manejado arriba
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MensajeError> manejarRuntime(
            RuntimeException ex,
            HttpServletRequest request) {

        MensajeError error = new MensajeError(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
