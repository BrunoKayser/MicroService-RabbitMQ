package br.com.brunokayser.myfood.cadastro.configuration;

import br.com.brunokayser.myfood.cadastro.dto.CustomErrorDto;
import com.br.brunokayser.myfood.cadastro.exception.BadRequestException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<CustomErrorDto> customHandlerBadRequest(Exception ex, WebRequest request){
        var error = getCustomError(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    private CustomErrorDto getCustomError(String messageError, int statusCode, Object... objects){

        return CustomErrorDto
            .builder()
            .messageError(messageError)
            .statusCode(statusCode)
            .timestamp(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime())
            .build();

    }
}
