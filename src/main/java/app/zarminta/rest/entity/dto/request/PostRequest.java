package app.zarminta.rest.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private String title;
    private String slug;
    private String content;
    private String image;
    private List<Integer> categoryId;
    private List<Integer> tagId;

}
