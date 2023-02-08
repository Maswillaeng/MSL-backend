package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.dto.user.request.UserJoinDTO;
import Maswillaeng.MSLback.dto.user.request.UserUpdateDTO;
import Maswillaeng.MSLback.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign")
    public ResponseEntity<Object> join(@RequestBody UserJoinDTO userJoinDTO) {
        User user = userJoinDTO.toEntity();

        log.info("userJoinDTO = {}", userJoinDTO);
        userService.join(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user") // 로그인 구현x
    public ResponseEntity<Object> update(@RequestBody UserUpdateDTO userUpdateDTO) {
        userService.update(1L, userUpdateDTO);
        return ResponseEntity.ok().build();
    }
}
