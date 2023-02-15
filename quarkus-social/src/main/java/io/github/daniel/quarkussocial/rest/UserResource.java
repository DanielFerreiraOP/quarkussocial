package io.github.daniel.quarkussocial.rest;

import io.github.daniel.quarkussocial.domain.model.User;
import io.github.daniel.quarkussocial.domain.repository.UserRepository;
import io.github.daniel.quarkussocial.rest.dto.CreateUserRequest;
import io.github.daniel.quarkussocial.rest.dto.ResponseError;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON) /*consumir objetos do tipo json*/
@Produces(MediaType.APPLICATION_JSON) /*retornar json nas respostas*/
public class UserResource {

    private UserRepository repository;
    private Validator validator;

    @Inject
    public UserResource(UserRepository repository, Validator validator){
        this.repository = repository;
        this.validator = validator;
    }

    @POST
    @Transactional                    /*abre uma trancisão com o bancod e dados para persistir*/
    public Response createUser( CreateUserRequest userRequest){

        //esse método retorna uma violação para o user request, sobre os campos que estão inválidos
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(userRequest);
        if (!violations.isEmpty()){
            return ResponseError.createFromValidation(violations).withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

        User user = new User();
        user.setAge(userRequest.getAge());
        user.setName(userRequest.getName());

        repository.persist(user);
        //alteração realizada para retornar o status 201, de usuário criado com sucesso
        return Response.status(Response.Status.CREATED.getStatusCode()).entity(user).build();
    }

    @GET
    public Response listAllUsers(){
        PanacheQuery<User> query = repository.findAll();
        return Response.ok(query.list()).build();   /*para listar as entidades*/
    }

    @DELETE
    @Path("{id}")
    @Transactional
    //  /users/1 -> passa o usuário e qual id vc quer excluir
    public Response deleteUser( @PathParam("id") Long id ){

        User user = repository.findById(id);
        if(user != null){
            repository.delete(user);
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    // mesma coisa que o de cima, manda o id na url, só que ele retorna os dados do usuário atualizado
    public Response updateUser( @PathParam("id") Long id, CreateUserRequest userData ){
        User user = repository.findById(id);

        if(user != null){
            user.setName(userData.getName());
            user.setAge(userData.getAge());
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
