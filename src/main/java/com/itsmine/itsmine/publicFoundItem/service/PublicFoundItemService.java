package com.itsmine.itsmine.publicFoundItem.service;

import com.itsmine.itsmine.publicFoundItem.batch.PublicFoundItemApiClient;
import com.itsmine.itsmine.publicFoundItem.dto.PublicFoundItemApiResponse;
import com.itsmine.itsmine.publicFoundItem.repository.PublicFoundItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicFoundItemService {

    private final PublicFoundItemApiClient publicFoundItemApiClient;


    public void fullSync() {

        PublicFoundItemApiResponse response = publicFoundItemApiClient.requestFoundItems(1, 10);
        log.info(response.toString());
    }
}
