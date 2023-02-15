package io.github.daniel.quarkussocial.rest.dto;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ResponseError {

    public static final int UNPROCESSABLE_ENTITY_STATUS = 422;

    //contem a menssagem
    private String message;
    //contem todos os erros
    private Collection<FieldError> errors;

    public ResponseError(String message, Collection<FieldError> errors) {
        this.message = message;
        this.errors = errors;
    }

    //método estático, que verrifica erro de objeto de qualquer tipo <T>
    public static <T> ResponseError createFromValidation( Set<ConstraintViolation<T>> violations ){

        //o metodo map ele vai mapear, ele recebe um erro de constraint violation e passa para field error, depois coloca esses erros numa lista
        List<FieldError> erros = violations.stream().map(cv -> new FieldError(cv.getPropertyPath().toString(), cv.getMessage())).collect(Collectors.toList());

        String message = "Validation Error";
        var responseError = new ResponseError(message, erros);
        return responseError;

    }

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}

    public Collection<FieldError> getErrors() {return errors;}

    public void setErrors(Collection<FieldError> errors) {this.errors = errors;}

    public Response withStatusCode(int code){
        return Response.status(code).entity(this).build();
    }
}
