package app.zarminta.rest.repository;

import app.zarminta.rest.entity.Category;
import app.zarminta.rest.entity.Post;
import app.zarminta.rest.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
