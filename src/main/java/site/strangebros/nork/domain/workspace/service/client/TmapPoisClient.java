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
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class TmapPoisClient {
    private static final String POIS_URL = "https://apis.openapi.sk.com/tmap/pois";

    @Value("${tmap.key}")
    private String key;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public List<Response> getPois(Request request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("appKey", key);

        ResponseEntity<Map> response = restTemplate.exchange(
                request.toUriComponents(POIS_URL).toUri(),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Map.class
        );

        List<Map<String, Object>> pois = parsePois(response);
        return pois.stream()
                .peek(System.out::println)
                .map(poi -> objectMapper.convertValue(poi, Response.class))
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> parsePois(ResponseEntity<Map> response) {
        Map<String, Object> body = response.getBody();
        Map<String, Object> searchPoiInfo = (Map<String, Object>) body.get("searchPoiInfo");
        Map<String, Object> pois = (Map<String, Object>) searchPoiInfo.get("pois");
        return (List<Map<String, Object>>) pois.get("poi");
    }

    @Setter
    @Getter
    @ToString
    public static class Request {
        private final String version = "1";
        private final String searchtypCd = "R"; // 거리순

        private String searchKeyword;
        private Double centerLon; // 경도
        private Double centerLat; // 위도
        private int radius; // 검색 반경
        private int page;
        private int count;

        @Builder
        public Request(String searchKeyword, Double centerLon, Double centerLat, int radius, int page, int count) {
            this.searchKeyword = searchKeyword;
            this.centerLon = centerLon;
            this.centerLat = centerLat;
            this.radius = radius;
            this.page = page;
            this.count = count;
        }

        public UriComponents toUriComponents(String url) {
            return UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("version", getVersion())
                    .queryParam("searchtypCd", getSearchtypCd())
                    .queryParam("searchKeyword", getSearchKeyword())
                    .queryParam("centerLon", getCenterLon())
                    .queryParam("centerLat", getCenterLat())
                    .queryParam("radius", getRadius())
                    .queryParam("page", getPage())
                    .queryParam("count", getCount())
                    .encode()
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @ToString
    public static class Response {
        private String id; // poi id
        private String name;
        private String noorLat; // 위도
        private String noorLon; // 경도
        private String upperBizName;
        private String middleBizName;
        private String lowerBizName;
        private Map<String, Object> newAddressList;

        public String getLatitude() {
            return noorLat;
        }

        public String getLongitude() {
            return noorLon;
        }

        public String getCategory() {
            return String.join("/", upperBizName, middleBizName, lowerBizName);
        }

        public String getRoadAddress() {
            return (String) (((List<Map<String, Object>>) newAddressList.get("newAddress")).get(0)).get("fullAddressRoad");
        }
    }

}
