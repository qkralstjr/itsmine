package com.itsmine.itsmine.publicFoundItem.util;

import com.itsmine.itsmine.publicFoundItem.domain.PublicFoundItem;
import com.itsmine.itsmine.publicFoundItem.dto.PublicFoundItemApiResponse;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PublicFoundItemConverter {

    public PublicFoundItem toEntity(PublicFoundItemApiResponse.Item dto){
        Instant instant = null;
        if(dto.getFdYmd() != null){
            try {
                LocalDate localDate = LocalDate.parse(dto.getFdYmd());
                instant = localDate.atStartOfDay(ZoneId.of("Asia/Seoul")).toInstant();
            } catch (DateTimeParseException e) {
                log.warn("[날짜 파싱 실패] atcId: {}, fdYmd: {}", dto.getAtcId(), dto.getFdYmd());
            }
        }

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
