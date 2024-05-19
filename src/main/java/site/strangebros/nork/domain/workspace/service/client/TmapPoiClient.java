package site.strangebros.nork.domain.workspace.service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class TmapPoiClient {
    private static final String POI_URL = "https://apis.openapi.sk.com/tmap/pois";

    @Value("${tmap.key}")
    private String key;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public Response getPoi(String poiId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("appKey", key);

        ResponseEntity<Map> response = restTemplate.exchange(
                buildURI(poiId),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Map.class
        );

        return parsePoi(response);
    }

    private String buildURI(String poiId) {
        return UriComponentsBuilder.fromHttpUrl(POI_URL)
                .queryParam("version", 1)
                .path("/{id}").build(poiId)
                .toString();
    }

    private Response parsePoi(ResponseEntity<Map> response) {
        Map<String, Object> body = response.getBody();
        Map<String, Object> poiDetailInfo = (Map<String, Object>) body.get("poiDetailInfo");

        return objectMapper.convertValue(poiDetailInfo, Response.class);
    }

    @Getter
    @Setter
    @Builder
    @ToString
    public static class Response {
        private String id; // poi id
        private String name;
        private String lat; // 위도
        private String lon; // 경도
        private String bizCatName;
        private String bldAddr;
        private String bldNo1;

        public String getLatitude() {
            return lat;
        }

        public String getLongitude() {
            return lon;
        }

        public String getCategory() {
            return bizCatName;
        }

        public String getRoadAddress() {
            return String.format("%s %s", bldAddr, bldNo1);
        }
    }

}
