package daos.tp.centroasistencias.errores;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Errores de validación (JSR-380)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> manejarValidaciones(MethodArgumentNotValidException ex,
                                                         HttpServletRequest request) {

        String mensaje = ex.getBindingResult()
                           .getAllErrors()
                           .get(0)
                           .getDefaultMessage();

        ErrorInfo error = new ErrorInfo(
                mensaje,
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Excepciones de negocio (por ejemplo: dni existe, nombre repetido, ciudad no encontrada)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorInfo> manejarRuntime(RuntimeException ex,
                                                    HttpServletRequest request) {

        ErrorInfo error = new ErrorInfo(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
