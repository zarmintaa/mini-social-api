package app.zarminta.rest.repository;

import app.zarminta.rest.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findBySlug(String slug);

    Boolean existsBySlug(String slug);
}
