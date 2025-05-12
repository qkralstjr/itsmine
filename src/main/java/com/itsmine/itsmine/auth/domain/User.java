package com.itsmine.itsmine.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "nickname", length = 8)
    private String nickname;

    @Column(name = "email", length = 30, unique = true)
    private String email;

    @Column(name = "phone", length = 13)
    private String phone;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "create_at")
    private Instant createAt;

    @Column(name = "provider", length = 10)
    private String provider;

}