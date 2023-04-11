package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Category;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.post.request.PostsSaveRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostsUpdateRequestDto;
import Maswillaeng.MSLback.dto.post.response.PostListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postsRepository;
    private final UserRepository userRepository;
    public Long savePost(Long userId, PostsSaveRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                        () -> new IllegalStateException("회원이 존재하지 않습니다. id=" + userId));

        Post post = requestDto.toEntity(user);
        postsRepository.save(post);
        return post.getId();
    }

    public Page<PostListResponseDto> getAllPosts(int page){
        Pageable pageable = PageRequest.of(page, 8, Sort.by("createdDate").descending());
        Page<Post> posts = postsRepository.findAll(pageable);
        List<PostListResponseDto> postList = posts.stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());

        return new PageImpl<>(postList, pageable, posts.getTotalElements());
    }

    public Page<PostListResponseDto> findPostsByUserId(Long userId, int page){
        userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("회원이 존재하지 않습니다. id=" + userId));

        Pageable pageable = PageRequest.of(page, 8, Sort.by("createdDate").descending());
        Page<Post> posts = postsRepository.findAllByUserId(userId, pageable);
        List<PostListResponseDto> postList = posts.stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
        return new PageImpl<>(postList, pageable, posts.getTotalElements());
    }

    public Page<PostListResponseDto> findPostsByNickname(String nickname, int page){
        userRepository.findByNickname(nickname).orElseThrow(
                () -> new IllegalStateException("회원이 존재하지 않습니다. nickname=" + nickname));

        Pageable pageable = PageRequest.of(page, 8, Sort.by("createdDate").descending());
        Page<Post> posts = postsRepository.findAllByUser_Nickname(nickname, pageable);
        List<PostListResponseDto> postList = posts.stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
        return new PageImpl<>(postList, pageable, posts.getTotalElements());
    }

    public Page<PostListResponseDto> findPostsByCategory(String category, int page) {
        Category enumCategory = Category.valueOf(category.toUpperCase());
        Pageable pageable = PageRequest.of(page, 8, Sort.by("createdDate").descending());
        Page<Post> posts = postsRepository.findAllByCategory(enumCategory, pageable);
        List<PostListResponseDto> postList = posts.stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
        return new PageImpl<>(postList, pageable, posts.getTotalElements());
    }

    public Post getPostById(Long postId){
        Post post = postsRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("게시물이 존재하지 않습니다. id=" +  postId));
        return post;
    }

    public void updatePost(Long postId, PostsUpdateRequestDto requestDto) {
        Post post = postsRepository.findById(postId)
                .orElseThrow(
                        ()-> new IllegalStateException("게시물이 존재하지 않습니다. id=" + postId));

        post.update(requestDto);
    }

    public void deletePost(Long postId) {
        Post post = postsRepository.findById(postId)
                .orElseThrow(
                        () -> new IllegalArgumentException("게시물이 존재하지 않습니다. id=" + postId)
                );
        postsRepository.delete(post);
    }

    public Map<String,String> uploadImage(MultipartFile imageFile) throws IOException {
        byte[] imageData = imageFile.getBytes();
        UUID uuid = UUID.randomUUID();
        String uploadDir = "MSL-back/src/main/resources/upload/";
        String savedFileName = uuid.toString() + "_" + imageFile.getOriginalFilename();
        Path path = Paths.get(uploadDir,savedFileName);

        Files.write(path, imageData);

        Map<String,String> imagePath = new HashMap<>();
        imagePath.put("img","/upload_img/"+savedFileName);

        return imagePath;
    }
}
