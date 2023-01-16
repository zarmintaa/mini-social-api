package app.zarminta.rest.controller;

import app.zarminta.rest.entity.dto.request.PostRequest;
import app.zarminta.rest.repository.PostRepository;
import app.zarminta.rest.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    @GetMapping("/all")
    public ResponseEntity<Object> getAll(){
        return postService.getAll();
    }

    @GetMapping
    public ResponseEntity<Object> getUserPosts(){
        return postService.getPostUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPostById(@PathVariable int id){
        return postService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Object> addPost(@RequestBody PostRequest postRequest){
        return postService.postData(postRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable int id, @RequestBody PostRequest postRequest){
        return postService.updateData(id, postRequest);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable int id){
        return postService.deleteData(id);
    }
}
