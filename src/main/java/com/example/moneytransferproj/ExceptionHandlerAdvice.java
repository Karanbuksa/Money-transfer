package com.example.moneytransferproj;

import com.example.moneytransferproj.exceptions.ConfirmationException;
import com.example.moneytransferproj.exceptions.ExceptionResponse;
import com.example.moneytransferproj.exceptions.InputDataException;
import com.example.moneytransferproj.exceptions.TransferException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@RestControllerAdvice
public class ExceptionHandlerAdvice {
    private static Integer errorCount;

    static {
        try {
            errorCount = Files.readAllLines(Paths.get("log/error.log")).size();
        } catch (IOException ignored) {

        }
    }

    @ExceptionHandler(InputDataException.class)
    public ResponseEntity<ExceptionResponse> invalidInputDataExceptionHandler(InputDataException e) {
//        log.error(e.getMessage());
        errorCount++;
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), errorCount),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransferException.class)
    public ResponseEntity<ExceptionResponse> transferExceptionHandler(TransferException e) {
//        log.error(e.getMessage());
        errorCount++;
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), errorCount),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConfirmationException.class)
    public ResponseEntity<ExceptionResponse> confirmationExceptionHandler(ConfirmationException e) {
//        log.error(e.getMessage());
        errorCount++;
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), errorCount),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
