package app.zarminta.rest.service;

import app.zarminta.rest.entity.Category;
import app.zarminta.rest.entity.Post;
import app.zarminta.rest.entity.Tag;
import app.zarminta.rest.entity.dto.request.PostRequest;
import app.zarminta.rest.entity.dto.response.MessageResponse;
import app.zarminta.rest.repository.CategoryRepository;
import app.zarminta.rest.repository.PostRepository;
import app.zarminta.rest.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private PostRepository postRepository;
    private EntityService entityService;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public ResponseEntity<Object> getAll(){
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(postRepository.findAll());
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
        return entityService.jsonResponse(HttpStatus.CREATED, post);
    }

    public ResponseEntity<Object> update(int id, PostRequest postRequest) {
        if (!postRepository.existsById(id)){
            return entityService
                    .jsonResponse(
                            HttpStatus.NOT_FOUND,
                            MessageResponse.builder()
                                    .message("Post with id = " + id + " not found!")
                                    .build()
                    );
        }
        var user = entityService.getUserLogged();
        Post post = getPost(id);
        return entityService.jsonResponse(HttpStatus.CREATED, post);
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

    public ResponseEntity<Object> updateData(int id, PostRequest postRequest) {
        return null;
    }

    public ResponseEntity<Object> deleteData(int id) {
        return null;
    }


}
