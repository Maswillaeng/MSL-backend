package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Follow;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.FollowRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
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


    public void saveFollow(Long userId, Long toUserId) {
        System.out.println("userId = " + userId);
        System.out.println("toUserId = " + toUserId);

        if(isFollow(userId, toUserId))
            throw new IllegalStateException("You are already subscribed.");

        User fromUser = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("This user does not exist"));
        User toUser = userRepository.findById(toUserId).orElseThrow(() -> new IllegalStateException("This user does not exist."));

        System.out.println("userId: " + userId + ", fromUser: " + fromUser);
        System.out.println("toUserId: " + toUserId + ", toUser: " + toUser);

        Follow follow = Follow.builder().toUser(toUser).fromUser(fromUser).build();

        followRepository.save(follow);
    }

    public void deleteFollow(Long userId, Long toUserId) {
        if (!isFollow(userId, toUserId)) {
            new IllegalStateException("구독중이 아닙니다.");
        }
        userRepository.findById(toUserId).orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        Follow follow = followRepository.findByFromUserIdAndToUserId(userId, toUserId);
        followRepository.delete(follow);
    }

    public boolean isFollow(Long fromUserId, Long toUserId) {
        return followRepository.existsByFromUserIdAndToUserId(fromUserId, toUserId);
    }

    public List<UserFollowResponseDto> findFollowingListByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        List<UserFollowResponseDto> followingList = followRepository.findAllByToUser(userId);
        return followingList;
    }

    public List<UserFollowResponseDto> findFollowingListByNickname(String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        List<UserFollowResponseDto> followingList = followRepository.findAllByFromUser(user.getId());
        return followingList;
    }

    public List<UserFollowResponseDto> findFollowerListByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        List<UserFollowResponseDto> followerList = followRepository.findAllByFromUser(userId);
        return followerList;
    }

    public List<UserFollowResponseDto> findFollowerListByNickname(String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        List<UserFollowResponseDto> followerList = followRepository.findAllByToUser(user.getId());
        return followerList;
    }
}
