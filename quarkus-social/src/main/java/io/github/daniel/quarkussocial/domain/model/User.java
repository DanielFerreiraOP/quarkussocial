package io.github.daniel.quarkussocial.domain.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id                                                   //indica o campo da private key, se não colocar vai dar erro
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //indica que o campo id é um auto incremento e integra ao banco de dados o incremento do valor
    private Long id;

    @Column(name = "name")                                  //o segundo "name", indica o nome que está no banco de dados
    private String name;

    @Column(name = "age")
    private Integer age;

}
