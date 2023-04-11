package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Category;
import Maswillaeng.MSLback.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByUserId(Long userId, Pageable pageable);

    Page<Post> findAllByUser_Nickname(String nickname, Pageable pageable);

    Page<Post> findAllByCategory(Category category, Pageable pageable);
    Page<Post> findAll(Pageable pageable);
}
