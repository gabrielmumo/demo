package dev.gabrielmumo.demo.exception;

import dev.gabrielmumo.demo.dto.ExceptionDto;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    private static final Log LOG = LogFactory.getLog(GlobalControllerAdvice.class);

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ExceptionDto> handleEntityNotFoundException(EntityNotFoundException e) {
        String message = e.getMessage();
        LOG.error(message, e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDto.Builder(message, HttpStatus.NOT_FOUND).build());
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ExceptionDto> handleBadRequestException(BadRequestException e) {
        String message = e.getMessage();
        LOG.error(message, e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto.Builder(message, HttpStatus.BAD_REQUEST).build());
    }
}