package Maswillaeng.MSLback.domain.entity;

import Maswillaeng.MSLback.domain.entity.key.PostLikeId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike {

    @EmbeddedId
    private PostLikeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @MapsId("postId")
    private Post post;

    @Builder
    public PostLike(User user, Post post) {
        this.id = new PostLikeId(user.getId(), post.getId());
        this.user = user;
        this.post = post;
    }
}