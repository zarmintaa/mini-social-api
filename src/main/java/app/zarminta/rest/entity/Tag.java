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
public class Tag {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "BIGINT")
    private BigInteger id;

    private String name;
    private String slug;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "tag_posts",
            joinColumns = @JoinColumn(name = "tag_"),
            inverseJoinColumns = @JoinColumn(name = "posts_"))
    private List<Post> posts = new ArrayList<>();
}
