package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.repository.PostQueryRepository;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchService {

    private final PostQueryRepository postQueryRepository;


    public Page<PostResponseDto> searchByKeyword(String keyword, int page) {
        return postQueryRepository.findByKeyword(keyword, PageRequest.of(page - 1, 20));

    }
}
