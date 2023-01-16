package app.zarminta.rest.repository;

import app.zarminta.rest.entity.Category;
import app.zarminta.rest.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
