package io.github.daniel.quarkussocial.rest;

import io.github.daniel.quarkussocial.domain.model.Post;
import io.github.daniel.quarkussocial.domain.model.User;
import io.github.daniel.quarkussocial.domain.repository.PostRepository;
import io.github.daniel.quarkussocial.domain.repository.UserRepository;
import io.github.daniel.quarkussocial.rest.dto.CreatePostRequest;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {


    private UserRepository userRepository;
    private PostRepository repository;

    @Inject
    public PostResource(UserRepository userRepository, PostRepository repository){
        this.userRepository = userRepository;
        this.repository = repository;
    }

    @POST
    @Transactional /* para injetar diretamente com o banco */
    public Response savePost(@PathParam("userId") Long userId, CreatePostRequest request){
        User user = userRepository.findById(userId);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        /*postagem*/
        Post post = new Post();
        post.setText(request.getText());
        post.setUser(user);

        /*Para fazer alteração diretamente na base*/
        repository.persist(post);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response listPosts(@PathParam("userId") Long userId){
        User user = userRepository.findById(userId);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().build();
    }

}