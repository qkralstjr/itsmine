package com.itsmine.itsmine.publicFoundItem.batch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsmine.itsmine.publicFoundItem.dto.PublicFoundItemApiResponse;
import com.itsmine.itsmine.publicFoundItem.dto.PublicFoundItemApiWrapper;
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

    public ResponseEntity<String> requestFoundItemsRaw(int pageNo, int pageSize) {

        try {
            URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("pageNo", pageNo)
                    .queryParam("numOfRows", pageSize)
                    .build(true).toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML));
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);

            return restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    requestEntity,
                    String.class
            );

        } catch (Exception e) {
            log.error("[API 요청 실패] page = {} - {}", pageNo, e.getMessage(), e);
            throw new RuntimeException("공공 API 요청 실패", e);
        }
    }

    public PublicFoundItemApiResponse parseJsonResponse(String content) {
        if (content == null || content.trim().isEmpty()) {
            log.error("[JSON 파싱 실패] 응답 내용이 비어있습니다.");
            throw new RuntimeException("API 응답이 비어있습니다.");
        }
        
        try {
            PublicFoundItemApiWrapper wrapper = objectMapper.readValue(content, PublicFoundItemApiWrapper.class);
            if (wrapper == null || wrapper.getResponse() == null) {
                log.error("[JSON 파싱 실패] 응답이 null입니다.");
                throw new RuntimeException("API 응답이 null입니다.");
            }
            return wrapper.getResponse();
        } catch (JsonProcessingException e) {
            log.error("[JSON 파싱 실패] 응답 내용: {}, 에러: {}", content, e.getMessage(), e);
            throw new RuntimeException("JSON 파싱 실패: " + e.getMessage(), e);
        }
    }

    public PublicFoundItemApiResponse parseXmlResponse(String content) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(PublicFoundItemApiResponse.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(content);
            return (PublicFoundItemApiResponse) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            log.error("[XML 파싱 실패] {}", e.getMessage(), e);
            throw new RuntimeException("XML 파싱 실패", e);
        }
    }

    public int getTotalPage(int pageSize){
        log.info("Response : {}", requestFoundItemsRaw(1,1).getBody());
        String body = requestFoundItemsRaw(1,1).getBody();
        PublicFoundItemApiResponse parsed = body != null && body.trim().startsWith("<")
                ? parseXmlResponse(body)
                : parseJsonResponse(body);
        int totalCount = parsed.getBody().getTotalCount();
        return (int) Math.ceil(totalCount / (double) pageSize);
    }

    public List<PublicFoundItemApiResponse.Item> fetchPage(int page){
        String body = requestFoundItemsRaw(page,1000).getBody();
        PublicFoundItemApiResponse parsed = body != null && body.trim().startsWith("<")
                ? parseXmlResponse(body)
                : parseJsonResponse(body);
        return parsed.getBody().getItems().getItem();
    }
}
