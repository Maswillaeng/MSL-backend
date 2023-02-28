package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByNickName(String nickName);

    @Query("select u from User u left join fetch u.followingList f1 left join fetch u.followerList f2 left join fetch u.postList p where u.id = :userId")
   User findJoinFollowingById(@Param("userId") Long userId);

    @Query("select u from User u join fetch u.followerList where u.id = :userId")
    User findJoinFollowerById(@Param("userId") Long userId);
}
