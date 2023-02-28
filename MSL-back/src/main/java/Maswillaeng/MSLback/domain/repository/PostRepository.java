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
    @Query(value = "select p from Post p join fetch p.user u where u.id = :userId",
            countQuery = "select count(p) from Post p where p.user.id = :userId")
    Page<Post> findByUserIdFetchJoin(@Param("userId") Long userId, PageRequest pageable);


    @Query(value = "delete from Post p where p.id in :postList")
    void deleteAllById(@Param("postList") List<Post> postList);
}
