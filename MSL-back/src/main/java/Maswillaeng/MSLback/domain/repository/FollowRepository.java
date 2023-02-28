package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Follow;
import Maswillaeng.MSLback.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    @Query("select f from Follow f join fetch f.following where f.follower.id =:userId")
    List<Follow> getFollowingList(@Param("userId") Long userId);

    @Query("select f from Follow f join fetch f.follower where f.following.id =:userId")
    List<Follow> getFollowerList(@Param("userId") Long userId);
}


