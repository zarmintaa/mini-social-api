package app.zarminta.rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BIGINT")
    private BigInteger id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User authorId;

    private String title;
    private String slug;
    private String content;
    private String image;

    @OneToMany(mappedBy = "post", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(mappedBy = "posts", fetch = FetchType.EAGER)
    private List<Category> categories = new ArrayList<>();

    @ManyToMany(mappedBy = "posts", fetch = FetchType.EAGER)
    private List<Tag> tags = new ArrayList<>();

}
