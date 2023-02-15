package io.github.daniel.quarkussocial.rest.dto;

public class FieldError {
    private String field;
    private String message;

    //construtor
    public FieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    //get and set
    public String getField() {return field;}

    public void setField(String field) {this.field = field;}

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}
}
