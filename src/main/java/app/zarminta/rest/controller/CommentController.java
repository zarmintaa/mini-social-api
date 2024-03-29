package app.zarminta.rest.controller;

import app.zarminta.rest.entity.dto.request.CommentRequest;
import app.zarminta.rest.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Object> addComment(@RequestBody CommentRequest commentRequest) {
        return commentService.addComment(commentRequest);
    }
}
