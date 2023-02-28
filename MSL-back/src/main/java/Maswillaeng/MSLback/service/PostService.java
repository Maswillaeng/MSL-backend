package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.HashTag;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.Tag;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.enums.Category;
import Maswillaeng.MSLback.domain.repository.*;
import Maswillaeng.MSLback.dto.post.reponse.PostDetailResponseDto;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import Maswillaeng.MSLback.dto.post.request.PostRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostUpdateDto;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostQueryRepository postQueryRepository;
    private final FollowService followService;
    private final HashTagService hashTagService;

    public void registerPost(Long userId, PostRequestDto postRequestDto) {
        User user = userRepository.findById(userId).get();
        Post post = postRequestDto.toEntity(user);

        List<HashTag> resultHashTagList =hashTagService.insertHashTagList(postRequestDto.getHashTagList(), post);

        post.setHashTagList(resultHashTagList);

        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostList(Category category) {

        return postQueryRepository.findAllPostByCategory(category);
    }

    public PostDetailResponseDto getPostById(Long postId) {
        Post post = postQueryRepository.findByIdFetchJoin(postId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시물입니다."));
        post.increaseHits();

        if (UserContext.userData.get() == null) {
            return new PostDetailResponseDto(post);
        } else {
            Long userId = UserContext.userData.get().getUserId();
            return new PostDetailResponseDto(post, userId);
        }

    }


    public void updatePost(Long userId, PostUpdateDto updateDto) throws Exception {
        Post selectedPost = postRepository.findById(updateDto.getPostId()).get();

        if (!Objects.equals(selectedPost.getUser().getId(), userId)) {
            throw new Exception("접근 권한 없음");
        }

        List<String> updateHashTagList = updateDto.getHashTagList();
       // List<Tag> updateTagList = tagRepository.findByNameList(updateHashTagList);
     List<HashTag> resultHashTagList =   hashTagService.updateHashTagList(updateHashTagList,selectedPost);

//
        selectedPost.setHashTagList(resultHashTagList);
        selectedPost.update(updateDto);

    }

    public void deletePost(Long userId, Long postId) throws ValidationException {
        Post post = postRepository.findById(postId).get();
        if (!Objects.equals(userId, post.getUser().getId())) {
            throw new ValidationException("접근 권한 없음");
        }

          List<String> deleteHashTag =  post.getHashTagList().stream().map(h->h.getTag().getName()).collect(Collectors.toCollection(ArrayList::new));
       hashTagService.deleteHashTagList(deleteHashTag,post);
       // hashTagRepository.deleteByPostId(post.getId());
        postRepository.delete(post);
       // tagRepository.deleteByIds(deleteHashTag);

    }


    @Transactional(readOnly = true)
    public Page<PostResponseDto> getUserPostList(Long userId, String category, int offset) {

        return postQueryRepository.findAllPostByUserIdAndCategory(userId, category,
                PageRequest.of(offset/20 - 1, 20));
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getReportedPostList(int page) {
        return postQueryRepository.findByReportCount(PageRequest.of(page - 1, 20));
    }
}
