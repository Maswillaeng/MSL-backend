package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("api/search")
    public ResponseEntity<?> search(@RequestParam String keyword,
                                    @RequestParam int page) {
        return ResponseEntity.ok().body(ResponseDto.of(
                "검색 결과 조회에 성공하였습니다",
                searchService.searchByKeyword(keyword, page)
        ));
    }

}
