package app.zarminta.rest.service;

import app.zarminta.rest.entity.Comment;
import app.zarminta.rest.entity.Post;
import app.zarminta.rest.entity.User;
import app.zarminta.rest.entity.dto.request.CommentRequest;
import app.zarminta.rest.entity.dto.request.PostRequest;
import app.zarminta.rest.entity.dto.response.MessageResponse;
import app.zarminta.rest.repository.CommentRepository;
import app.zarminta.rest.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;

    private final PostRepository postRepository;

    private final EntityService  entityService;

    public ResponseEntity<Object> addComment( CommentRequest commentRequest){
        User userLogged = entityService.getUserLogged();
 /*
        System.out.println(userLogged);
        if (!postRepository.existsById(commentRequest.getPostId())){
            return entityService
                    .jsonResponse(
                            HttpStatus.NOT_FOUND,
                            MessageResponse.builder()
                                    .message("Post with id = " + commentRequest.getPostId() + " not found")
                                    .build()
                    );
        }*/
        Comment comment = Comment
                .builder()
                .post(postRepository.findById(commentRequest.getPostId()).get())
                .content(commentRequest.getContent())
                .user(userLogged).build();
        commentRepository.save(comment);
        return entityService.jsonResponse(HttpStatus.CREATED, comment);
    }

}
