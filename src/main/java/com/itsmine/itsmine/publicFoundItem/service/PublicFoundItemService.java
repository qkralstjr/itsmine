package com.itsmine.itsmine.publicFoundItem.service;

import com.itsmine.itsmine.publicFoundItem.domain.PublicFoundItem;
import com.itsmine.itsmine.publicFoundItem.repository.PublicFoundItemRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicFoundItemService {

    private final PublicFoundItemRepository repository;

    @Transactional
    public void saveSafe(PublicFoundItem item){
        try {
            repository.save(item);
        } catch (DataIntegrityViolationException e){
            log.warn("중복 생략 : {}", item.getAtcId());
        } catch (ObjectOptimisticLockingFailureException e) {
            log.warn("[락 충돌 생략] : {}", item.getAtcId());
        }

    }

}
