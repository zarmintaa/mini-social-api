package app.zarminta.rest;

import app.zarminta.rest.entity.dto.request.AuthenticationRequest;
import app.zarminta.rest.entity.dto.request.CategoryRequest;
import app.zarminta.rest.entity.dto.request.RegisterRequest;
import app.zarminta.rest.entity.dto.request.TagRequest;
import app.zarminta.rest.service.AuthenticationService;
import app.zarminta.rest.service.CategoryService;
import app.zarminta.rest.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final AuthenticationService authenticationService;
    private final TagService tagService;
    private final CategoryService categoryService;

    @Override
    public void run(String... args) throws Exception {
        tagService.postData(TagRequest.builder().name("FirstPost").slug("first-post").build());
        tagService.postData(TagRequest.builder().name("ProgrammingWithMe").slug("programming-with-me").build());
        tagService.postData(TagRequest.builder().name("BlackLiveMatters").slug("black-live-matters").build());

        categoryService.postData(CategoryRequest.builder().title("coding").slug("coding").build());
        categoryService.postData(CategoryRequest.builder().title("Programming Tools").slug("programming-tools").build());
        categoryService.postData(CategoryRequest.builder().title("UI Design").slug("ui-design").build());

        authenticationService.register(RegisterRequest.builder().firstname("Mamang").lastname("Gamink").email("mamang@gmail.com").password("123").build());
    }
}
