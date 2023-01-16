package app.zarminta.rest.service;

import app.zarminta.rest.entity.Category;
import app.zarminta.rest.entity.dto.request.CategoryRequest;
import app.zarminta.rest.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category getById(int id){
        return categoryRepository
                .findById(id)
                .get();
    }

    public Category postData(CategoryRequest categoryRequest){
        var category = Category
                .builder()
                .title(categoryRequest.getTitle())
                .slug(categoryRequest.getSlug())
                .build();
        return categoryRepository.save(category);
    }
}
