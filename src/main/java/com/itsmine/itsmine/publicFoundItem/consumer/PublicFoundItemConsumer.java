package com.itsmine.itsmine.publicFoundItem.consumer;

import com.itsmine.itsmine.publicFoundItem.domain.PublicFoundItem;
import com.itsmine.itsmine.publicFoundItem.service.PublicFoundItemService;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublicFoundItemConsumer implements InitializingBean {

    private final BlockingQueue<PublicFoundItem> queue;
    private final PublicFoundItemService service;
    private final ExecutorService executor;

    @Override
    public void afterPropertiesSet() {
        for(int i = 0; i < 5; i++){
            executor.submit(this::consume);
        }
    }

    private void consume(){
        while(true){
            try {
                PublicFoundItem item = queue.take();
                service.saveSafe(item);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}
