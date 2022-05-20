package cucumber.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class RestTemplateUtil {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String baseUrl = "http://localhost:8080/fmh";

    public ResponseEntity<String> getRq(String jwt, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "*/*");
        headers.set(HttpHeaders.AUTHORIZATION, jwt);

        RequestEntity<Void> requestEntity = RequestEntity
                .get(baseUrl + url)
                .headers(headers)
                .build();
        return restTemplate.exchange(requestEntity, String.class);
    }

    public ResponseEntity<String> postRq(String body, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");

        RequestEntity<String> requestEntity = RequestEntity
                .post(baseUrl + url)
                .headers(headers)
                .body(body);;
        return restTemplate.exchange(Objects.requireNonNull(requestEntity), String.class);

    }

    public ResponseEntity<String> postRq(String jwt, String body, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "*/*");
        headers.set(HttpHeaders.AUTHORIZATION, jwt);
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");

        RequestEntity<String> requestEntity = RequestEntity
                .post(baseUrl + url)
                .headers(headers)
                .body(body);;
        return restTemplate.exchange(Objects.requireNonNull(requestEntity), String.class);
    }

    public ResponseEntity<String> putRq(String jwt, String body, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "*/*");
        headers.set(HttpHeaders.AUTHORIZATION, jwt);
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");

        RequestEntity<String> requestEntity = RequestEntity
                .put(baseUrl + url)
                .headers(headers)
                .body(body);;
        return restTemplate.exchange(Objects.requireNonNull(requestEntity), String.class);
    }

    public ResponseEntity<Void> deleteRq(String jwt, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "*/*");
        headers.set(HttpHeaders.AUTHORIZATION, jwt);

        RequestEntity<Void> requestEntity = RequestEntity
                .delete(baseUrl + url)
                .headers(headers)
                .build();
        return restTemplate.exchange(requestEntity, Void.class);
    }

}
