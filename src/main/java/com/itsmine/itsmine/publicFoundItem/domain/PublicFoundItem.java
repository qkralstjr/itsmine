package com.itsmine.itsmine.publicFoundItem.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "public_found_items")
@NoArgsConstructor
@Getter
@ToString
public class PublicFoundItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pfId;

    private String atcId;
    private String category;
    private String color;
    private String description;
    private String name;
    private String location;
    @Column(name = "img_path")
    private String imgPath;
    @Column(name = "found_at")
    private Date foundAt;

    @Builder
    public PublicFoundItem(Long pfId, String atcId, String category, String color, String description, String name, String location, String imgPath, Date foundAt){
        this.pfId = pfId;
        this.atcId = atcId;
        this.category = category;
        this.color = color;
        this.description = description;
        this.name = name;
        this.location = location;
        this.imgPath = imgPath;
        this.foundAt = foundAt;
    }
}
