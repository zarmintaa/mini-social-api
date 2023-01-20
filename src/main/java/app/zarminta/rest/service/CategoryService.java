package app.zarminta.rest.service;

import app.zarminta.rest.entity.Category;
import app.zarminta.rest.entity.Post;
import app.zarminta.rest.entity.dto.request.CategoryRequest;
import app.zarminta.rest.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final EntityService entityService;

    public Category getById(int id) {
        return categoryRepository
                .findById(id)
                .get();
    }

    public ResponseEntity<Object> getPostsByCategory(String slug) {
        if (!categoryRepository.existsBySlug(slug)) {
            return entityService.jsonResponse(HttpStatus.NOT_FOUND, "Category with slug = " + slug + " not found");
        }
        List<Post> posts = categoryRepository.findBySlug(slug).get().getPosts();
        return entityService.jsonResponse(HttpStatus.OK, posts);
    }

    public ResponseEntity<Object> findCategory(int id) {
        if (!categoryRepository.existsById(id)) {
            return entityService.jsonResponse(HttpStatus.NOT_FOUND, "Category with id = " + id + " not found");
        }
        return entityService.jsonResponse(HttpStatus.OK, getById(id));
    }

    public Category postData(CategoryRequest categoryRequest) {
        var category = Category
                .builder()
                .title(categoryRequest.getTitle())
                .slug(categoryRequest.getSlug())
                .build();
        return categoryRepository.save(category);
    }

    public ResponseEntity<Object> updateCategory(int id, CategoryRequest categoryRequest) {
        if (!categoryRepository.existsById(id)) {
            return entityService.jsonResponse(HttpStatus.NOT_FOUND, "Category with id = " + id + " not found");
        }
        Category category = getById(id);
        category.setTitle(categoryRequest.getTitle());
        category.setSlug(categoryRequest.getSlug());
        Category response = categoryRepository.save(category);
        return entityService.jsonResponse(HttpStatus.CREATED, response);
    }

    public ResponseEntity<Object> deleteById(int id) {
        if (!categoryRepository.existsById(id)) {
            return entityService.jsonResponse(HttpStatus.NOT_FOUND, "Category with id = " + id + " not found");
        }
        Category category = getById(id);
        categoryRepository.deleteById(id);
        return entityService.jsonResponse(HttpStatus.OK, "Delete category with id = " + id + " success!");
    }
}
