package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.reponse.LoginResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.TokenResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.UserApiResponse;
import Maswillaeng.MSLback.dto.user.reponse.UserInfoResponseDto;
import Maswillaeng.MSLback.dto.user.request.LoginRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final FollowService followService;
    @Transactional(readOnly = true)
    public UserInfoResponseDto getUser(Long userId) {
        User user = userRepository.findJoinFollowingById(userId);
        if(UserContext.userData.get()==null){
            return new UserInfoResponseDto(user);
        }else {
            User apiUser = userRepository.findJoinFollowerById(UserContext.userData.get().getUserId());
           boolean isFollowed  = followService.alreadyFollow(apiUser,userId);
            return new UserInfoResponseDto(user,isFollowed);
        }

    }

    public void updateUser(Long userId, UserUpdateRequestDto requestDto) {
        User selectedUser = userRepository.findById(userId).get();

        selectedUser.update(requestDto); // 더티체킹
//        userRepository.save(selectedUser);
    }

    public void userWithdraw(Long userId) {
        User findUser = userRepository.findById(userId).get();
        findUser.withdraw();
    }

    public UserApiResponse getUserApi(Long userId) {
       User user = userRepository.findById(userId).get();
       return new UserApiResponse(user,true);
    }
}
