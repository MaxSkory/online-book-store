package mskory.bookstore.exception;

import jakarta.persistence.EntityNotFoundException;
import java.net.URI;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        ProblemDetail problemDetail = getProblemDetail(status, ex.getBody().getDetail());
        ex.getBindingResult().getAllErrors().forEach(e -> setError(problemDetail, e));
        return ResponseEntity.of(problemDetail).build();
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode statusCode,
            @NonNull WebRequest request) {
        ProblemDetail problemDetail = getProblemDetail(statusCode, ex.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSqlException(SQLException ex) {
        ProblemDetail problemDetail = getProblemDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setType(
                URI.create("https://docs.intersystems.com/latest/csp/docbook/DocBook.UI.Page.cls?KEY=RERR_sql"));
        problemDetail.setProperty("SQL error code", ex.getErrorCode());
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        ProblemDetail problemDetail = getProblemDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.of(problemDetail).build();
    }

    private ProblemDetail getProblemDetail(HttpStatusCode statusCode, String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(statusCode, message);
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return problemDetail;
    }

    private void setError(ProblemDetail problemDetail, ObjectError error) {
        if (error instanceof FieldError fieldError) {
            String fieldName = ((FieldError) error).getField();
            List<String> errorMessage = createFieldErrorMessage(problemDetail, fieldError);
            problemDetail.setProperty(fieldName, errorMessage);
        } else {
            problemDetail.setProperty(error.getObjectName(), error.getDefaultMessage());
        }
    }

    private List<String> createFieldErrorMessage(ProblemDetail problemDetail, FieldError error) {
        List<String> errorMessages = new ArrayList<>();
        Map<String, Object> errors = problemDetail.getProperties();
        if (errors != null && errors.containsKey(error.getField())) {
            errorMessages = (List<String>) errors.get(error.getField());
            errorMessages.add(errorMessages.size() - 2, error.getDefaultMessage());
        } else {
            errorMessages.add(error.getDefaultMessage());
            errorMessages.add("rejected value = " + error.getRejectedValue());
        }
        return errorMessages;
    }
}
