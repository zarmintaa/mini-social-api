package app.zarminta.rest.service;

import app.zarminta.rest.entity.*;
import app.zarminta.rest.entity.dto.request.CommentRequest;
import app.zarminta.rest.entity.dto.request.PostRequest;
import app.zarminta.rest.entity.dto.response.MessageResponse;
import app.zarminta.rest.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class PostService {
    private PostRepository postRepository;
    private EntityService entityService;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public ResponseEntity<Object> getAll(){
        List<Post> posts = postRepository.findAll();
        if (posts.isEmpty()){
            return entityService.jsonResponse(HttpStatus.OK, MessageResponse.builder().message("No one post found!").build());
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(posts);
    }

    public ResponseEntity<Object> getPostUser(){
        User userLogged = entityService.getUserLogged();
        List<Post> posts = userLogged.getPosts();
        if (posts.isEmpty()){
            return entityService.jsonResponse(HttpStatus.OK, MessageResponse.builder().message("User doesn't have any posts").build());
        }
        return entityService.jsonResponse(HttpStatus.OK, posts);
    }

    public ResponseEntity<Object> getById(int id){
        boolean exists = postRepository.existsById(id);
        if (!exists){
            return entityService
                    .jsonResponse(
                            HttpStatus.NOT_FOUND,
                            MessageResponse.builder()
                                    .message("Post with id = " + id + " not found")
                                    .build()
                    );
        }
        Post post = postRepository.findById(id).get();
        return entityService
                .jsonResponse(HttpStatus.OK, post);
    }

    public Post getPost(int id){
        return postRepository.findById(id).get();
    }

    public ResponseEntity<Object> postData(PostRequest postRequest) {
        var user = entityService.getUserLogged();
        var post = Post
                .builder()
                .title(postRequest.getTitle())
                .slug(postRequest.getSlug())
                .authorId(user)
                .image(postRequest.getImage())
                .content(postRequest.getContent())
                .tags(extractTags(postRequest.getTagId()))
                .categories(extractCategory(postRequest.getCategoryId()))
                .build();
        return entityService.jsonResponse(HttpStatus.CREATED, postRepository.save(post));
    }



    public ResponseEntity<Object> updateData(int id, PostRequest postRequest) {
        if (!postRepository.existsById(id)){
            return entityService
                    .jsonResponse(
                            HttpStatus.NOT_FOUND,
                            MessageResponse.builder()
                                    .message("Post with id = " + id + " not found!")
                                    .build()
                    );
        }
        Post post = getPost(id);
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setImage(postRequest.getImage());
        post.setSlug(postRequest.getSlug());
        post.setCategories(extractCategory(postRequest.getCategoryId()));
        post.setTags(extractTags(postRequest.getTagId()));
        Post updatedPost = postRepository.save(post);
        return entityService.jsonResponse(HttpStatus.CREATED, updatedPost);
    }

    public List<Category> extractCategory(List<Integer> integers){
        List<Category> categories = new ArrayList<>();
        for (Integer integer : integers) {
            categories.add(categoryRepository.findById(integer).get());
        }
        return categories;
    }
    
    public List<Tag> extractTags(List<Integer> integers){
        List<Tag> tags = new ArrayList<>();
        for (Integer integer : integers) {
            tags.add(tagRepository.findById(integer).get());
        }
        return tags;
    }


    public ResponseEntity<Object> deleteData(int id) {
        if (!postRepository.existsById(id)){
            return entityService
                    .jsonResponse(
                            HttpStatus.NOT_FOUND,
                            MessageResponse.builder()
                                    .message("Post with id = " + id + " not found!")
                                    .build()
                    );
        }
        Post post = getPost(id);
        postRepository.deleteById(id);
        return entityService.jsonResponse(HttpStatus.NOT_FOUND, post);
    }

}
