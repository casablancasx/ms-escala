package br.gov.agu.nutec.msescala.exceptions.handler;

import br.gov.agu.nutec.msescala.exceptions.ResourceAlreadyExistsException;
import br.gov.agu.nutec.msescala.exceptions.ResourceNotFoundException;
import br.gov.agu.nutec.msescala.exceptions.SemPautistaDisponivelException;
import br.gov.agu.nutec.msescala.exceptions.UnprocessableEntityException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {



    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(
                java.time.Instant.now(),
                404,
                "Resource Not Found",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(404).body(err);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<StandardError> resourceAlreadyExists(ResourceAlreadyExistsException e, HttpServletRequest request) {
        StandardError err = new StandardError(
                java.time.Instant.now(),
                409,
                "Resource Already Exists",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(409).body(err);
    }

    @ExceptionHandler(SemPautistaDisponivelException.class)
    public ResponseEntity<StandardError> semPautistaDisponivel(SemPautistaDisponivelException e, HttpServletRequest request) {
        StandardError err = new StandardError(
                java.time.Instant.now(),
                400,
                "Sem Pautista Disponível",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(400).body(err);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<StandardError> unprocessableEntity(UnprocessableEntityException e, HttpServletRequest request) {
        StandardError err = new StandardError(
                java.time.Instant.now(),
                422,
                "Unprocessable Entity",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(422).body(err);
    }



}
