package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.user where p.id = :id")
    Optional<Post> findByIdFetchJoin(@Param("id") Long id);

    @Query("select new Maswillaeng.MSLback.dto.post.reponse.PostResponseDto(" +
            "p.id, u.id, u.nickName, u.userImage, p.thumbnail, p.title, p.content," +
            " p.createdAt, p.modifiedAt, count(c), count(l), p.hits) " +
            "from Post p "
            + "join p.user u "
            + "left join p.commentList c "
            + "left join p.postLikeList l "
            + "group by p.id "
            + "order by p.createdAt desc")
    List<PostResponseDto> findAllPostResponseDto(Pageable pageable);




    @Query(value = "select p from Post p join fetch p.user u where u.id = :userId",
            countQuery = "select count(p) from Post p where p.user.id = :userId")
    Page<Post> findByUserIdFetchJoin(@Param("userId") Long userId, PageRequest pageable);

}
