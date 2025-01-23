package com.example.jpa.domain.member.entity;

import com.example.jpa.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Member  extends BaseEntity {

    @Column(length = 100, unique = true)
    private String username;

    @Column(length = 100)
    private String password;

    @Column(length = 100)
    private String nickName;
}
