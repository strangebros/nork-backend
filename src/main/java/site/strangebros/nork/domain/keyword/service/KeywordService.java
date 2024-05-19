package site.strangebros.nork.domain.keyword.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.strangebros.nork.domain.keyword.mapper.KeywordMapper;
import site.strangebros.nork.domain.keyword.service.dto.response.KeywordResponse;

@RequiredArgsConstructor
@Service
public class KeywordService {

    private final KeywordMapper keywordMapper;

    public List<KeywordResponse> findAll() {
        return keywordMapper.findAll().stream()
                .map(KeywordResponse::from)
                .toList();
    }

}
