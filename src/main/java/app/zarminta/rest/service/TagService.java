package app.zarminta.rest.service;

import app.zarminta.rest.entity.Tag;
import app.zarminta.rest.entity.dto.request.TagRequest;
import app.zarminta.rest.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TagService {
    private final TagRepository tagRepository;
    private final EntityService entityService;

    public Tag getById(int id) {
        return tagRepository.findById(id).get();
    }

    public Tag postData(TagRequest tagRequest) {
        var tag = Tag
                .builder()
                .name(tagRequest.getName())
                .slug(tagRequest.getSlug())
                .build();
        tagRepository.save(tag);
        return tag;
    }

    public Tag getTag(int id){
        return tagRepository.findById(id).get();
    }

    public ResponseEntity<Object> getTagById(int id) {
        if (!tagRepository.existsById(id)) {
            return entityService.jsonResponse(HttpStatus.NOT_FOUND, "Tag with id = " + id + " not found!");
        }
        return entityService.jsonResponse(HttpStatus.OK, getTagById(id));
    }

    public ResponseEntity<Object> updateTag(int id, TagRequest tagRequest) {
        if (!tagRepository.existsById(id)) {
            return entityService.jsonResponse(HttpStatus.NOT_FOUND, "Tag with id = " + id + " not found!");
        }
        Tag tag = getById(id);
        tag.setSlug(tagRequest.getSlug());
        tag.setName(tagRequest.getName());
        Tag response = tagRepository.save(tag);
        return entityService.jsonResponse(HttpStatus.CREATED, response);
    }

    public ResponseEntity<Object> deleteTag(int id) {
        if (!tagRepository.existsById(id)) {
            return entityService.jsonResponse(HttpStatus.NOT_FOUND, "Tag with id = " + id + " not found!");
        }
        tagRepository.deleteById(id);
        return entityService.jsonResponse(HttpStatus.OK, "Success delete tag with id = " + id);
    }

}
