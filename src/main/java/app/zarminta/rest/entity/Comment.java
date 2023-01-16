package app.zarminta.rest.entity;

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
@Table(name = "post_comment")
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BIGINT")
    private BigInteger id;
    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    private String content;


    @ManyToMany
    @JoinTable(name = "post_comment_users",
            joinColumns = @JoinColumn(name = "comment_"),
            inverseJoinColumns = @JoinColumn(name = "users_id"))
    private List<User> users = new ArrayList<>();
}
