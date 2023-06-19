package com.example.moneytransferproj;

import com.example.moneytransferproj.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
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
        return getResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransferException.class)
    public ResponseEntity<ExceptionResponse> transferExceptionHandler(TransferException e) {
        return getResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConfirmationException.class)
    public ResponseEntity<ExceptionResponse> confirmationExceptionHandler(ConfirmationException e) {
        return getResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ResponseEntity<ExceptionResponse> getResponseEntity(MyExceptionAncestor e, HttpStatus error) {
        log.error(e.getMessage() + " (" + error + ")." + " Транзакция: " + e.getTransaction().getOperationID());
        errorCount++;
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), errorCount),
                error);
    }
}
