package dev.gabrielmumo.demo.dto;

import org.springframework.http.HttpStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public record ExceptionResponse(String message, HttpStatus status, String date) {

    public static final class Builder {
        String message;
        HttpStatus status;

        public Builder(String message, HttpStatus status) {
            this.message = message;
            this.status = status;
        }

        public ExceptionResponse build() {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            return new ExceptionResponse(message, status, dateFormat.format(date));
        }
    }
}
