package br.com.brunokayser.myfood.cadastro.configuration;

import br.com.brunokayser.myfood.cadastro.dto.CustomErrorDto;
import com.br.brunokayser.myfood.cadastro.exception.BadRequestException;
import com.br.brunokayser.myfood.cadastro.exception.NotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    @Qualifier("customMessageSource")
    private MessageSource messageSource;


    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<CustomErrorDto> customHandlerBadRequest(BadRequestException ex, WebRequest request){
        var error = getCustomError(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<CustomErrorDto> customHandlerNotFound(NotFoundException ex, WebRequest request){
        var error = getCustomError(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    private CustomErrorDto getCustomError(String messageError, int statusCode, Object... objects){

        return CustomErrorDto
            .builder()
            .messageError(getFormattedMessage(messageError, objects))
            .statusCode(statusCode)
            .timestamp(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime())
            .build();

    }

    private String getFormattedMessage(String message, Object... objects) {
        try {
            return messageSource.getMessage(message, objects, Locale.US);
        } catch (Exception ex) {
            log.info(ex.getMessage());
            return message;
        }
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
        HttpStatus status, WebRequest request) {

        CustomErrorDto errors = CustomErrorDto
            .builder()
            .timestamp(LocalDateTime.now())
            .statusCode(status.value())
            .messageError(getFormattedMessage(ex.getBindingResult().getFieldError().getDefaultMessage()))
            .build();

        return new ResponseEntity<>(errors, headers, status);
    }
}
