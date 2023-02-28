package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Comment;
import Maswillaeng.MSLback.domain.entity.CommentLike;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.entity.key.CommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId> {

    boolean existsByUserAndComment(User user, Comment comment);
}
