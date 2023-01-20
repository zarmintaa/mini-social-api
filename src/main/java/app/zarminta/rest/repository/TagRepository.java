package app.zarminta.rest.repository;

import app.zarminta.rest.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findBySlug(String slug);

    Boolean existsBySlug(String slug);
}
