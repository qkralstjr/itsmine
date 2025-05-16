package com.itsmine.itsmine.publicFoundItem.repository;

import com.itsmine.itsmine.publicFoundItem.domain.PublicFoundItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicFoundItemRepository extends JpaRepository<PublicFoundItem, Long> {

}
