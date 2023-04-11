package Maswillaeng.MSLback.domain.entity;

import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String phoneNumber;

    private String userImage;

    @Column(length = 100)
    private String introduction;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();

    @ColumnDefault("0")
    private int withdrawYn;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    private String refreshToken;

    private LocalDateTime withdrawAt;

    @OneToMany(mappedBy = "toUser", fetch = FetchType.LAZY)
    private Set<Follow> followerList = new HashSet<>();

    @OneToMany(mappedBy = "fromUser", fetch = FetchType.LAZY)
    private Set<Follow> followingList = new HashSet<>();


    @Builder
    public User(String email, String password, String nickname, String phoneNumber, String userImage,
                String introduction) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.userImage = userImage;
        this.introduction = introduction;
        this.withdrawYn = 0;
        this.role = RoleType.USER;
    }


    public void update(UserUpdateRequestDto requestDto) {
        this.password = requestDto.getPassword();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.nickname = requestDto.getNickname();
        this.userImage = requestDto.getUserImage();
        this.introduction = requestDto.getIntroduction();
    }

    public void updatePw(String encodePwd) {
        this.password = encodePwd;
    }

    public void updateRole(RoleType role){
        this.role = role;
    }
    public void updateRefreshToken(String token) {
        this.refreshToken = token;
    }
    public void deleteRefreshToken() {
        this.refreshToken = null;
    }
    public void updateProfileImage(String userImage) {
        this.userImage = userImage;
    }
}
