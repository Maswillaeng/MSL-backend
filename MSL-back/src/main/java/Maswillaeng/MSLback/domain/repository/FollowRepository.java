package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Follow;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.dto.user.response.UserFollowListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

//    @Query("SELECT new Maswillaeng.MSLback.dto.user.response.UserFollowListResponseDto(u)" +
//            " FROM Follow f " +
//            "JOIN f.toUser u " +
//            "WHERE f.fromUser.id = :userId")
//    List<UserFollowListResponseDto> findAllByFromUser(@Param("userId") Long userId);
//
//    @Query("SELECT new Maswillaeng.MSLback.dto.user.response.UserFollowListResponseDto(u) " +
//            "FROM Follow f " +
//            "JOIN f.fromUser u " +
//            "WHERE f.toUser.id = :userId")
//    List<UserFollowListResponseDto> findAllByToUser(@Param("userId") Long userId);

    @Query("SELECT new Maswillaeng.MSLback.dto.user.response.UserFollowListResponseDto(u)" +
            " FROM Follow f " +
            "JOIN f.toUser u " +
            "WHERE f.fromUser = :user")
    List<UserFollowListResponseDto> findAllByFromUser(@Param("user") User user);

    @Query("SELECT new Maswillaeng.MSLback.dto.user.response.UserFollowListResponseDto(u) " +
            "FROM Follow f " +
            "JOIN f.fromUser u " +
            "WHERE f.toUser = :user")
    List<UserFollowListResponseDto> findAllByToUser(@Param("user") User user);

    int countByToUserId(Long fromUserId); // 팔로워 수 (follower)
    int countByFromUserId(Long toUserId);// 팔로우 수 (following)

    boolean existsByFromUserAndToUser(User fromUser, User toUser);
    Follow findByFromUserAndToUser(User fromUser, User toUser);
}
