package site.strangebros.nork.domain.workspace.service.client;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class NaverImageSearchClient {
    private static final String URL = "https://openapi.naver.com/v1/search/image";

    @Value("${naver.id}")
    private String id;

    @Value("${naver.key}")
    private String key;

    private final RestTemplate restTemplate;

    public List<String> getImageUrls(String query) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", id);
        headers.set("X-Naver-Client-Secret", key);

        ResponseEntity<Map> response = restTemplate.exchange(
                buildURI(query).toUri(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Map.class
        );

        return parse(response);
    }

    private UriComponents buildURI(String query) {
        return UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("query", query)
                .encode()
                .build();
    }

    private List<String> parse(ResponseEntity<Map> response) {
        Map<String, Object> body = response.getBody();
        List<Map<String, Object>> items = (List<Map<String, Object>>) body.get("items");

        return items.stream()
                .map(item -> (String) item.get("link"))
                .toList();
    }
}
