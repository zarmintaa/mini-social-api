package app.zarminta.rest.service;

import app.zarminta.rest.entity.Tag;
import app.zarminta.rest.entity.dto.request.TagRequest;
import app.zarminta.rest.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TagService {
    private final TagRepository tagRepository;

    public Tag getById(int id){
        return tagRepository.findById(id).get();
    }

    public Tag postData(TagRequest tagRequest){
        var tag = Tag
                .builder()
                .name(tagRequest.getName())
                .slug(tagRequest.getSlug())
                .build();
        return tagRepository.save(tag);
    }
}
