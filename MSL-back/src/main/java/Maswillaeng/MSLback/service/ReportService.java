package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.common.exception.EntityNotFoundException;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.Report;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.domain.repository.ReportRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    public void saveReport(Long postId) {
        User user = userRepository.findById(UserContext.userData.get().getUserId()).get();
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 게시물입니다")
        );

        if (reportRepository.existsByUserAndPost(user, post)) {
            throw new IllegalStateException("이미 신고한 게시물입니다");
        }

        Report report = Report.builder()
                .user(user)
                .post(post).build();
        reportRepository.save(report);
    }

    public void deleteReport(Long postId) {
        User user = userRepository.findById(UserContext.userData.get().getUserId()).get();
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 게시물입니다"));
        Report report = reportRepository.findByUserAndPost(user, post).orElseThrow(
                () -> new IllegalStateException("신고하지 않은 게시물입니다"));

        reportRepository.delete(report);
    }
}
