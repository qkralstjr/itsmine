package com.itsmine.itsmine.publicFoundItem.service;

import com.itsmine.itsmine.publicFoundItem.batch.PublicFoundItemApiClient;
import com.itsmine.itsmine.publicFoundItem.domain.PublicFoundItem;
import com.itsmine.itsmine.publicFoundItem.dto.PublicFoundItemApiResponse;
import com.itsmine.itsmine.publicFoundItem.dto.PublicFoundItemApiResponse.Item;
import com.itsmine.itsmine.publicFoundItem.repository.PublicFoundItemRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicFoundItemMultiSyncServcie {

    private final PublicFoundItemApiClient apiClient;
    private final PublicFoundItemRepository repository;

    private static final int THREAD_POLL_SIZE = 5;
    private static final int PAGE_SIZE = 1000;

    public void multiThreadedSync() {
        log.info("[멀티스레드 Sync] 시작");


        PublicFoundItemApiResponse firstPage = apiClient.requestFoundItems(1,1);
        if(firstPage.getBody() == null){
            log.error("[멀티스레드 Sync] 첫 페이지 응답 없음");
            return;
        }

        int totalCount = firstPage.getBody().getTotalCount();
        int totalPages = (int) Math.ceil(totalCount / (double) PAGE_SIZE);

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POLL_SIZE);
        CountDownLatch countDownLatch = new CountDownLatch(totalPages);
        for(int i = 1; i <= totalPages; i++){
            final int page = i;
            executor.submit(()->{
                long start = System.currentTimeMillis();
                try {
                    syncAndSavePage(page);
                } catch (Exception e) {
                    log.error("[멀티스레드 syncAndSavePage] {}Page 실패 : {} - {}", page, e.getClass().getName(), e.getMessage(), e);
                } finally {
                    long end = System.currentTimeMillis();
                    log.info("[page {}] 작업 소요 시간: {} ms", page, (end - start));
                    countDownLatch.countDown();
                }
            });
        }

        executor.shutdown();
        try {
            countDownLatch.await();
        }catch (InterruptedException e){
            log.error("멀티스레드 수집 대기 중단됨 {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    @Transactional
    public void syncAndSavePage(int pageNo) {
        PublicFoundItemApiResponse response = apiClient.requestFoundItems(pageNo, PAGE_SIZE);
        if(response.getBody() == null ||
            response.getBody().getItems()  == null ||
            response.getBody().getItems().getItem() == null){
            log.warn("[page {} API 응답 오류, 저장 생략]", pageNo);
            return;
        }
        List<Item> items = response.getBody().getItems().getItem();

//        List<String> atcIdList = items.stream().map(Item::getAtcId).collect(Collectors.toList());
//        log.info("atcIdList : {}", atcIdList);

        List<PublicFoundItem> entities = items.stream()
                .filter(item -> item.getAtcId() != null)
                .collect(Collectors.toMap(
                        Item::getAtcId,
                        item -> item,
                        (e1, e2) -> e1                  // 단일 스레드 내부에서만 중복제거
                ))
                .values().stream().toList().stream().map(this::toEntity).toList();

        try {
            repository.saveAll(entities);
        } catch (DataIntegrityViolationException  e) {
            log.warn("[page {}] saveAll 중복 발생. 단건 저장으로 재시도", pageNo);
            for (PublicFoundItem item : entities) {
                try {
                    repository.save(item);
                } catch (DataIntegrityViolationException ex) {
                    log.warn("[중복 atcId 생략] : {}", item.getAtcId());
                }
            }
        }
        log.info("[page {}] 저장 완료: {}건", pageNo, entities.size());
    }

    private PublicFoundItem toEntity(PublicFoundItemApiResponse.Item dto){
        String dateStr = dto.getFdYmd();
        LocalDate localDate = LocalDate.parse(dateStr);
        Instant instant = localDate.atStartOfDay(ZoneId.of("Asia/Seoul")).toInstant();
        return PublicFoundItem.builder()
                .atcId(dto.getAtcId())
                .category(dto.getFdPrdtNm())
                .color(dto.getClrNm())
                .description(dto.getFdSbjt())
                .name(dto.getFdPrdtNm())
                .location(dto.getDepPlace())
                .imgPath(dto.getFdFilePathImg())
                .foundAt(instant)
                .build();
    }


}
