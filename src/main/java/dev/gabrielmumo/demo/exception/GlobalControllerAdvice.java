package dev.gabrielmumo.demo.exception;

import dev.gabrielmumo.demo.dto.ExceptionDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    private static final Log LOG = LogFactory.getLog(GlobalControllerAdvice.class);

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<ExceptionDto> handleUnauthorizedException(UnauthorizedException e) {
        String message = "Unauthorized";
        LOG.error(message, e);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionDto.Builder(message, HttpStatus.UNAUTHORIZED).build());
    }
}
