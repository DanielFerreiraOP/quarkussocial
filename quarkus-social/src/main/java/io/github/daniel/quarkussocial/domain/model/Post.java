package io.github.daniel.quarkussocial.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_text")
    private String text;

    @Column(name = "dateTime")
    private LocalDateTime dateTime;

    @ManyToOne /*varias postagens para um usuário, houve a necessidade de trocar manytomany para manytoone*/
    @JoinColumn(name = "user_id") /*para entender que é um relacionamento, com chave estrangeira*/
    private User user;

}
