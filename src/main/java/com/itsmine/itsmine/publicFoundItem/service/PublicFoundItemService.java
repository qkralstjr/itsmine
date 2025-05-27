package com.itsmine.itsmine.publicFoundItem.service;

import com.itsmine.itsmine.publicFoundItem.batch.PublicFoundItemApiClient;
import com.itsmine.itsmine.publicFoundItem.domain.PublicFoundItem;
import com.itsmine.itsmine.publicFoundItem.dto.PublicFoundItemApiResponse;
import com.itsmine.itsmine.publicFoundItem.repository.PublicFoundItemRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicFoundItemService {

    private final PublicFoundItemApiClient publicFoundItemApiClient;
    private final PublicFoundItemRepository publicFoundItemRepository;

/*
    @Transactional
    public void fullSync() {
        PublicFoundItemApiResponse response = publicFoundItemApiClient.requestFoundItems(1, 10);
//        log.info(response.toString());
        List<PublicFoundItemApiResponse.Item> items = response.getBody().getItems().getItem();
        log.info("습득물 API 응답 건수: {}", items.size());
        List<PublicFoundItem> entities = items.stream()
                        .map(this::toEntity)
                        .collect(Collectors.toList());
        try {
            publicFoundItemRepository.saveAll(entities);
        }catch (RuntimeException e) {
            log.error("습득물 API 응답 데이터 DB 저장 실패: {}",e.getMessage());
        }
        log.info("DB 저장 요청 완료: {}건", entities.size());
    }
*/

}
