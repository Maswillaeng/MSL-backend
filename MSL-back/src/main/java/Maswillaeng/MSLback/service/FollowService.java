package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Follow;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.FollowRepository;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    /**
     * 팔로우 기능
     *
     * @param myId
     * @param userId
     */
    public void addFollow(Long myId, Long userId) {
        User myAccount = userRepository.findById(myId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        User yourAccount = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        Optional<Follow> find = followRepository.findByFollowerIdAndFollowingId(myId, userId);
        if (find.isPresent()) {
            throw new IllegalStateException("이미 팔로우한 회원입니다.");
        }

        Follow follow = Follow.builder()
                .follower(myAccount)
                .following(yourAccount)
                .build();

        followRepository.save(follow);
    }

    /**
     * 팔로우 취소 기능
     */
    public void unFollow(Long myId, Long userId) {
        User myAccount = userRepository.findById(myId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        User yourAccount = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        Follow follow = Follow.builder()
                .follower(myAccount)
                .following(yourAccount)
                .build();

        followRepository.delete(follow);
    }

}
