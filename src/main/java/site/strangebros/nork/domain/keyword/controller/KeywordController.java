package site.strangebros.nork.domain.keyword.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.strangebros.nork.domain.keyword.service.KeywordService;
import site.strangebros.nork.domain.keyword.service.dto.response.KeywordResponse;
import site.strangebros.nork.global.web.dto.response.SuccessResponse;

@RequiredArgsConstructor
@RequestMapping("/keywords")
@RestController
public class KeywordController {

    private final KeywordService keywordService;

    @GetMapping
    public SuccessResponse<List<KeywordResponse>> findAll() {
        return SuccessResponse.ok(keywordService.findAll());
    }

}
