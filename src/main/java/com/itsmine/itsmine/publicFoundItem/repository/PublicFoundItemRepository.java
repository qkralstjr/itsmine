package com.itsmine.itsmine.publicFoundItem.repository;

import com.itsmine.itsmine.publicFoundItem.domain.PublicFoundItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicFoundItemRepository extends JpaRepository<PublicFoundItem, Long> {
    Optional<PublicFoundItem> findByAtcId(String atcId);
    List<PublicFoundItem> findByAtcIdIn(List<String> atcIds);
}
