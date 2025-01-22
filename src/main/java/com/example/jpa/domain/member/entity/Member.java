package com.example.jpa.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @CreatedDate
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime modifiedDate;

    @Column(length = 100, unique = true)
    private String username;

    @Column(length = 100)
    private String password;

    @Column(length = 100)
    private String nickName;
}
