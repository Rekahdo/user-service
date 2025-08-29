package com.rekahdo.user_service.exceptions.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.BindException;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Getter
public class ErrorResponse {

    private interface SingleErrorView {};

    private interface MultiErrorView extends SingleErrorView {};

    @JsonView(SingleErrorView.class)
    private String exception;

    @JsonView(SingleErrorView.class)
    private String line;

    @JsonView(SingleErrorView.class)
    private int code;

    @JsonView(SingleErrorView.class)
    private HttpStatus status;

    @JsonView(SingleErrorView.class)
    private String message;

    @JsonView(MultiErrorView.class)
    private List<String> messages;

    @JsonView(SingleErrorView.class)
    private int errorCount = 1;

    @JsonView(SingleErrorView.class)
    private String description;

    @JsonView(SingleErrorView.class)
    private Instant timestamp = Instant.now();

    @JsonView(SingleErrorView.class)
    private StackTraceElement[] trace;

    @JsonIgnore
    private boolean manyErrors;

    public ErrorResponse(Exception ex, HttpStatusCode statusCode, WebRequest request) {
        this.exception = ex.getClass().getSimpleName();
        this.line = fetchLine(ex);
        this.code = statusCode.value();;
        this.status = HttpStatus.valueOf(statusCode.value());
        this.description = request.getDescription(true);

        if(ex instanceof BindException){
            this.manyErrors = true;
            errorCount = ((BindException) ex).getErrorCount();
            messages = ((BindException) ex).getAllErrors().stream()
                    .map(objectError -> {
                        assert objectError.getDefaultMessage() != null;
                        return objectError.getDefaultMessage();
                    }).toList();
            message = messages.getFirst();
        }
        else {
            errorCount = 1;
            message = ex.getMessage();
            messages = Collections.emptyList();
        }
    }

    public MappingJacksonValue fetchMJV() {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(this);
        mappingJacksonValue.setSerializationView((manyErrors ?
                MultiErrorView.class : SingleErrorView.class));
        return mappingJacksonValue;
    }

    private String fetchLine(Exception ex){
        StackTraceElement[] stackTrace = ex.getStackTrace();
        return stackTrace.length > 0
                ? String.format("%s.%s:line-%d", stackTrace[0].getClassName(),
                stackTrace[0].getMethodName(), stackTrace[0].getLineNumber())
                : "Unknown";
    }

}