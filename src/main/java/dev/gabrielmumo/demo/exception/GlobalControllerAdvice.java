package dev.gabrielmumo.demo.exception;

import dev.gabrielmumo.demo.dto.ExceptionDto;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    private static final Log LOG = LogFactory.getLog(GlobalControllerAdvice.class);

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ExceptionDto> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDto.Builder(handleExceptionMessage(e), HttpStatus.NOT_FOUND).build());
    }

    @ExceptionHandler({BadRequestException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionDto> handleBadRequestException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto.Builder(handleExceptionMessage(e), HttpStatus.BAD_REQUEST).build());
    }

    @ExceptionHandler({BadCredentialsException.class, UnauthorizedException.class})
    public ResponseEntity<ExceptionDto> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionDto.Builder(handleExceptionMessage(e), HttpStatus.UNAUTHORIZED).build());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionDto> handleUnexpectedException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionDto.Builder(handleExceptionMessage(e), HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    private String handleExceptionMessage(Exception e) {
        String message = e.getMessage();
        LOG.error(message, e);
        return message;
    }
}
