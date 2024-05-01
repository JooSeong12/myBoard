package com.example.myBoard.entity;

import com.example.myBoard.constant.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //findById
    private String name;  //findByName
    private String email;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name="like_color")
    private String likeColor;  //findByLikeColor
    @Column(name="created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;  //findByCreatedAt
    @Column(name="updated_at", insertable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
