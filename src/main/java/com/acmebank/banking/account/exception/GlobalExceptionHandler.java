package com.acmebank.banking.account.exception;

import com.acmebank.banking.account.dto.ErrorResponseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });

        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                validationErrors.toString(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO,  HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }

        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                errors.toString(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO,  HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity handleBusinessException(BusinessException exception,
                                                  WebRequest webRequest){
        BusinessException businessEx = (BusinessException) exception;

        String errorMsg = (exception.getMessage() == null) ? exception.getClass().getSimpleName() : exception.getMessage();

        log.error("[" + businessEx.getErrorCode() + "] " + businessEx.getMessage());

        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false),
                businessEx.getHttpStatus(),
                errorMsg,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, businessEx.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception,
                                                                            WebRequest webRequest) {
        log.error(exception.getMessage(), exception);

        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
