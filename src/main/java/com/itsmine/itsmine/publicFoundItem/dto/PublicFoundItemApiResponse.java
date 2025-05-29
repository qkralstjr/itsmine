package com.itsmine.itsmine.publicFoundItem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;


@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class PublicFoundItemApiResponse {

    @XmlElement(name = "header")
    private Header header;
    @XmlElement(name = "body")
    private Body body;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Header {
        @XmlElement
        private String resultCode;
        @XmlElement
        private String resultMsg;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @XmlAccessorType(XmlAccessType.FIELD)
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public static class Body {
        @XmlElement
        private int totalCount;
        @XmlElement
        private int pageNo;
        @XmlElement
        private int numOfRows;

        @JsonProperty("items")
        @XmlElementWrapper(name = "items")
        @XmlElement(name = "item")
        private ItemsWrapper items;

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class ItemsWrapper {
            @JsonProperty("item")
            @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)     // item이 하나 일땐 배열이 아닌 객체로 인식해서 오류 남 ==> 코드 추가
            @XmlElement(name = "item")
            private List<Item> item;
        }
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Item {
        @XmlElement
        private String atcId;
        @XmlElement
        private String clrNm;
        @XmlElement
        private String depPlace;
        @XmlElement
        private String fdFilePathImg;
        @XmlElement
        private String fdPrdtNm;
        @XmlElement
        private String fdSbjt;
        @XmlElement
        private int fdSn;
        @XmlElement
        private String fdYmd;
        @XmlElement
        private String prdtClNm;
        @XmlElement
        private int rnum;
    }
}


