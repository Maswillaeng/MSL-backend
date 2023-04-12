package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Follow;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.FollowRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.response.UserFollowListResponseDto;
import Maswillaeng.MSLback.dto.user.response.UserFollowResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;


    public UserFollowResponseDto saveFollow(Long userId, String nickname) {
        User fromUser = userRepository.findById(userId).get();
        User toUser = userRepository.findByNickname(nickname).orElseThrow(() -> new IllegalStateException("This user does not exist"));
        if(isFollow(fromUser, toUser))
            throw new IllegalStateException("You are already subscribed.");

        Follow follow = Follow.builder().toUser(toUser).fromUser(fromUser).build();
        followRepository.save(follow);

        UserFollowResponseDto responseDto = UserFollowResponseDto.builder()
                .userId(userId)
                .otherUserId(toUser.getId())
                .isFollow(true)
                .myFollowerCnt(countFollower(userId))
                .myFollowingCnt(countFollowing(userId))
                .otherFollowerCnt(countFollower(nickname))
                .otherFollowingCnt(countFollowing(nickname))
                .build();
        return responseDto;
    }

    public UserFollowResponseDto deleteFollow(Long userId, String nickname) {
        User fromUser = userRepository.findById(userId).get();
        User toUser = userRepository.findByNickname(nickname).orElseThrow(() -> new IllegalStateException("This user does not exist"));

        if (!isFollow(fromUser, toUser)) {
            new IllegalStateException("구독중이 아닙니다.");
        }

        Follow follow = followRepository.findByFromUserAndToUser(fromUser, toUser);
        followRepository.delete(follow);

        UserFollowResponseDto responseDto = UserFollowResponseDto.builder()
                .userId(userId)
                .otherUserId(toUser.getId())
                .isFollow(false)
                .myFollowerCnt(countFollower(userId))
                .myFollowingCnt(countFollowing(userId))
                .otherFollowerCnt(countFollower(nickname))
                .otherFollowingCnt(countFollowing(nickname))
                .build();
        return responseDto;
    }

    public boolean isFollow(User fromUser, User toUser) {
        return followRepository.existsByFromUserAndToUser(fromUser, toUser);
    }

    public List<UserFollowListResponseDto> findFollowingListByNickname(String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        List<UserFollowListResponseDto> followingList = followRepository.findAllByFromUser(user);
        return followingList;
    }

    public List<UserFollowListResponseDto> findFollowerListByNickname(String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        List<UserFollowListResponseDto> followerList = followRepository.findAllByToUser(user);
        return followerList;
    }

    public int countFollowing (Long userId) {
        return followRepository.countByFromUserId(userId);
    }

    public int countFollower (Long userId) {
        return followRepository.countByToUserId(userId);
    }

    public int countFollowing (String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        return followRepository.countByFromUserId(user.getId());
    }

    public int countFollower (String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        return followRepository.countByToUserId(user.getId());
    }

    public List<UserFollowListResponseDto> findFollowingListByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        List<UserFollowListResponseDto> followingList = followRepository.findAllByFromUser(user);
        return followingList;
    }

    public List<UserFollowListResponseDto> findFollowerListByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        List<UserFollowListResponseDto> followerList = followRepository.findAllByFromUser(user);
        return followerList;
    }
}
