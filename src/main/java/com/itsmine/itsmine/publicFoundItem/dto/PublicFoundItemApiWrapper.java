package com.itsmine.itsmine.publicFoundItem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicFoundItemApiWrapper {
    private PublicFoundItemApiResponse response;
}
