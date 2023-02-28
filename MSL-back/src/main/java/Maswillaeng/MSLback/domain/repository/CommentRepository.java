package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c" +
            " join fetch c.user u" +
            " left join c.commentLikeList cl" +
            " where c.parentId.id = :parentId")
    List<Comment> findByParentId(@Param("parentId") Long parentId);
}
