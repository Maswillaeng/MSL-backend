package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Like;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserAndPost(User user, Post post);
    void deleteLikeByUserAndPost(User user, Post post);
}
