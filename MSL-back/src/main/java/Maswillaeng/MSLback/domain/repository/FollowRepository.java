package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Follow;
import Maswillaeng.MSLback.dto.user.response.UserFollowResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query("SELECT new Maswillaeng.MSLback.dto.user.response.UserFollowResponseDto(u)" +
            " FROM Follow f " +
            "JOIN f.toUser u " +
            "WHERE f.fromUser.id = :userId")
    List<UserFollowResponseDto> findAllByFromUser(@Param("userId") Long userId);

    @Query("SELECT new Maswillaeng.MSLback.dto.user.response.UserFollowResponseDto(u) " +
            "FROM Follow f " +
            "JOIN f.fromUser u " +
            "WHERE f.toUser.id = :userId")
    List<UserFollowResponseDto> findAllByToUser(@Param("userId") Long userId);

    Long countByToUserId(Long fromUserId); // 팔로워 수 (follower)
    Long countByFromUserId(Long toUserId);// 팔로우 수 (following)

    boolean existsByFromUserIdAndToUserId(Long fromUserId, Long toUserId);
    Follow findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);
}
