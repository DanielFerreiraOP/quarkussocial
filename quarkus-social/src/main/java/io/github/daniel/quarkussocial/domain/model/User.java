package io.github.daniel.quarkussocial.domain.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id                                                   //indica o campo da private key, se não colocar vai dar erro
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //indica que o campo id é um auto incremento e integra ao banco de dados o incremento do valor
    private Long id;

    @Column(name = "name")                                  //o segundo "name", indica o nome que está no banco de dados
    private String name;

    @Column(name = "age")
    private Integer age;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public Integer getAge() {return age;}
    public void setAge(Integer age) {this.age = age;}

    @Override                                                 //alt+insert e escolha a opção (equals), metodo para comparação de objetos
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(age, user.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }
}
