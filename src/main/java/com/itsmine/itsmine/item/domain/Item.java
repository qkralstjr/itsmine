package com.itsmine.itsmine.item.domain;

import com.itsmine.itsmine.chat.domain.Chatroom;
import com.itsmine.itsmine.notification.domain.Notification;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    private Integer id;

    @Column(name = "User_id")
    private Integer userId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "color", length = 100)
    private String color;

    @Column(name = "category", length = 100)
    private String category;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "occurrence_at")
    private LocalDate occurrenceAt;

    @Column(name = "status")
    private String status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "reg_at")
    private Instant regAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "update_at")
    private Instant updateAt;

    @Column(name = "item_type")
    private String itemType;

    @OneToMany(mappedBy = "item")
    private Set<Chatroom> chatrooms = new LinkedHashSet<>();

    @OneToMany(mappedBy = "item")
    private Set<Image> images = new LinkedHashSet<>();

    @OneToMany(mappedBy = "item")
    private Set<Notification> notifications = new LinkedHashSet<>();

}