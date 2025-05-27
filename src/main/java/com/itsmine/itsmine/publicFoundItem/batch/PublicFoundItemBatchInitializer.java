package com.itsmine.itsmine.publicFoundItem.batch;


import com.itsmine.itsmine.publicFoundItem.service.PublicFoundItemMultiSyncServcie;
import com.itsmine.itsmine.publicFoundItem.service.PublicFoundItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/*
*  서버 실행 직후 1회 fullSync batch
* */
@Component
@RequiredArgsConstructor
@Slf4j
public class PublicFoundItemBatchInitializer implements ApplicationRunner {

    private final PublicFoundItemMultiSyncServcie multiSyncServcie;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("[PublicFoundItemBatchInitializer] 애플리케이션 구동");

        try {
            multiSyncServcie.multiThreadedSync();
        } catch (Exception e) {
            log.error("[PublicFoundItemBatchInitializer] fullSync() 실패 : {}", e.getMessage());
        }

    }
}
