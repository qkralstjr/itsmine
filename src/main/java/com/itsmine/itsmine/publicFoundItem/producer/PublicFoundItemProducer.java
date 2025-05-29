package com.itsmine.itsmine.publicFoundItem.producer;

import com.itsmine.itsmine.publicFoundItem.batch.PublicFoundItemApiClient;
import com.itsmine.itsmine.publicFoundItem.domain.PublicFoundItem;
import com.itsmine.itsmine.publicFoundItem.dto.PublicFoundItemApiResponse;
import com.itsmine.itsmine.publicFoundItem.dto.PublicFoundItemApiResponse.Item;
import com.itsmine.itsmine.publicFoundItem.util.PublicFoundItemConverter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PublicFoundItemProducer {

    private final PublicFoundItemApiClient apiClient;
    private final BlockingQueue<PublicFoundItem> queue;
    private final PublicFoundItemConverter converter;

    public void produceAll(int pageSize){
        int totalPages = apiClient.getTotalPage(pageSize);

        for(int page = 1; page <= totalPages; page++){
            List<Item> rawItems = apiClient.fetchPage(page);
            Map<String, PublicFoundItem> deduped = rawItems.stream()
                    .filter(i -> i.getAtcId() != null)
                    .collect(Collectors.toMap(
                            Item::getAtcId,
                            converter::toEntity,
                            (i1, i2) -> i1
                    ));

            for (PublicFoundItem item : deduped.values()) {
                try {
                    queue.put(item);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }



}
