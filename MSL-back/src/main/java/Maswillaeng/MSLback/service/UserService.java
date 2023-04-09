package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.response.UserResponseDto;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {


    private final UserRepository userRepository;
    private final PostService postService;


//    public List<User> findAllUsers(){
//        return userRepository.findAll();
//    }


    public UserResponseDto findByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return UserResponseDto.builder().user(user).build();
    }

    public UserResponseDto findByNickname(String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return UserResponseDto.builder().user(user).build();
    }
    public void updateUser(Long userId, UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(userId).get();
        user.update(requestDto);
    }


    public void deleteByUserId(Long userId){
        userRepository.deleteById(userId);
    }

    public Map<String,String> uploadUserImage(MultipartFile imageFile) throws IOException {
        Map<String,String> image = postService.uploadImage(imageFile);

        return image;
    }
}