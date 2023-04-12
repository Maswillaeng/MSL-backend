package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.Like;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.LikeRepository;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class LikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository postLikeRepository;

    public void saveLike(Long userId, Long postId) {
        User user = userRepository.findById(userId).get();
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 게시물입니다"));

        if(isLiked(user, post))
            throw new IllegalStateException("You are already Liked.");

        Like like = Like.builder()
                .user(user)
                .post(post).build();

        postLikeRepository.save(like);
    }

    public void deleteLike(Long userId, Long postId) {
        User user = userRepository.findById(userId).get();
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 게시물입니다"));

        if(!isLiked(user, post))
            throw new IllegalStateException("You are not Liked.");

        postLikeRepository.deleteLikeByUserAndPost(user, post);
    }
    public boolean isLiked(User user, Post post){
        return postLikeRepository.existsByUserAndPost(user, post);
    }
//
//    public void saveLike(User user, Post post) {
//        Like like = Like.builder()
//                .user(user)
//                .post(post).build();
//        postLikeRepository.save(like);
//    }
//
//    public void deleteLike(User user, Post post) {
//        postLikeRepository.deleteLikeByUserAndPost(user, post);
//    }
}
