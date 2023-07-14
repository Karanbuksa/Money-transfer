package com.example.moneytransferproj.handler;

import com.example.moneytransferproj.exceptions.ConfirmationException;
import com.example.moneytransferproj.exceptions.ExceptionResponse;
import com.example.moneytransferproj.exceptions.InputDataException;
import com.example.moneytransferproj.exceptions.TransferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {
    private static final AtomicInteger errorCount = new AtomicInteger();

    public ExceptionHandlerAdvice() throws IOException {
        errorCount.set(Files.readAllLines(Paths.get("log/error.log"), Charset.forName("Windows-1251")).size());
    }

    @ExceptionHandler(InputDataException.class)
    public ResponseEntity<ExceptionResponse> invalidInputDataExceptionHandler(InputDataException e) {
        log.error(MessageFormat.format("{0} ({1}). Транзакция: {2}", e.getMessage(), HttpStatus.BAD_REQUEST, e.getTransaction().getOperationID()));
        errorCount.incrementAndGet();
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), errorCount.get()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransferException.class)
    public ResponseEntity<ExceptionResponse> transferExceptionHandler(TransferException e) {
        log.error(MessageFormat.format("{0} ({1}). Транзакция: {2}", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getTransaction().getOperationID()));
        errorCount.incrementAndGet();
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), errorCount.get()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConfirmationException.class)
    public ResponseEntity<ExceptionResponse> confirmationExceptionHandler(ConfirmationException e) {
        log.error(MessageFormat.format("{0} ({1}). Транзакция: {2}", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getTransaction().getOperationID()));
        errorCount.incrementAndGet();
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage(), errorCount.get()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
