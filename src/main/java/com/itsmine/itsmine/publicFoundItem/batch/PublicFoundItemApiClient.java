package com.itsmine.itsmine.publicFoundItem.batch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsmine.itsmine.publicFoundItem.dto.PublicFoundItemApiResponse;
import java.io.StringReader;
import java.net.URI;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.InvalidUrlException;
import org.springframework.web.util.UriComponentsBuilder;

/*
*  공공기관 습득물 API 호출 Client
* */
@Component
@RequiredArgsConstructor
@Slf4j
public class PublicFoundItemApiClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${api.lostFoundService.url}")
    private String baseUrl;

    @Value("${api.lostFoundService.key}")
    private String serviceKey;

    public PublicFoundItemApiResponse requestFoundItems(int pageNo, int pageSize) {

        try {
            URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("pageNo", pageNo)
                    .queryParam("numOfRows", pageSize)
                    .build(true).toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    requestEntity,
                    String.class
            );
            String rawJson = responseEntity.getBody();
            log.info("공공 API 응답(rawJson_: {}", rawJson);

            return objectMapper.readValue(rawJson, PublicFoundItemApiResponse.class);

        } catch (HttpClientErrorException e) {
            log.error("4xx 클라이언트 오류 발생: status={}, body={}", e.getStatusCode(),
                    e.getResponseBodyAsString());
            throw new RuntimeException("공공 API 요청 실패: 잘못된 요청입니다.", e);

        } catch (HttpServerErrorException e) {
            log.error("5xx 서버 오류 발생: status={}, body={}", e.getStatusCode(),
                    e.getResponseBodyAsString());
            throw new RuntimeException("공공 API 요청 실패: 서버 오류입니다.", e);

        } catch (ResourceAccessException e) {
            log.error("네트워크 연결 실패 또는 타임아웃: {}", e.getMessage());
            throw new RuntimeException("공공 API 네트워크 오류", e);

        } catch (RestClientException e) {
            log.error("RestTemplate 오류: {}", e.getMessage());
            throw new RuntimeException("공공 API 호출 실패", e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
