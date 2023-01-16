package app.zarminta.rest.repository;

import app.zarminta.rest.entity.Comment;
import app.zarminta.rest.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
