package com.itsmine.itsmine.publicFoundItem.dto;

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
/*
XML to Java Object
@Getter
@Setter
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class PublicFoundItemApiResponse  {
    @XmlElement(name = "header")
    private Header header;

    @XmlElement(name = "body")
    private Body body;

    @Getter
    @Setter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Header {
        @XmlElement(name = "resultCode")
        private String resultCode;

        @XmlElement(name = "resultMsg")
        private String resultMsg;
    }

    @Getter
    @Setter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Body {
        @XmlElementWrapper(name = "items")
        @XmlElement(name = "item")
        private List<Item> items;

        @XmlElement(name = "numOfRows")
        private int numOfRows;

        @XmlElement(name = "pageNo")
        private int pageNo;

        @XmlElement(name = "totalCount")
        private int totalCount;
    }

    @Getter
    @Setter
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Item {
        @XmlElement(name = "atcId")
        private String atcId;                   // 관리ID

        @XmlElement(name = "clrNm")
        private String clrNm;                   // 색상명

        @XmlElement(name = "depPlace")
        private String depPlace;                // 보관장소

        @XmlElement(name = "fdFilePathImg")
        private String fdFilePathImg;           // 습득물 사진 이미지

        @XmlElement(name = "fdPrdtNm")
        private String fdPrdtNm;                // 물품명

        @XmlElement(name = "fdSbjt")
        private String fdSbjt;                  // 게시제목

        @XmlElement(name = "fdSn")
        private String fdSn;                    // 습득순번

        @XmlElement(name = "fdYmd")
        private String fdYmd;                   // 습득일자

        @XmlElement(name = "prdtClNm")
        private String prdtClNm;                // 물품분류명

        @XmlElement(name = "rnum")
        private String rnum;                    // item 번호
    }
}
*/

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicFoundItemApiResponse {

    private Header header;
    private Body body;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Body {
        private int totalCount;
        private int pageNo;
        private int numOfRows;

        @JsonProperty("items")
        private ItemsWrapper items;

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class ItemsWrapper {
            @JsonProperty("item")
            private List<Item> item;
        }
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        private String atcId;
        private String clrNm;
        private String depPlace;
        private String fdFilePathImg;
        private String fdPrdtNm;
        private String fdSbjt;
        private int fdSn;
        private String fdYmd;
        private String prdtClNm;
        private int rnum;
    }
}


