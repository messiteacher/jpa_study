package com.example.jpa.domain.post.post.entity;

import com.example.jpa.domain.post.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @CreatedDate
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime modifiedDate;

    @Column(length = 100)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String body;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.ALL, CascadeType.PERSIST}) // mappedBy를 사용하지 않은 쪽이 관계의 주인
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

    public void removeComment(long id) {

        Optional<Comment> opComment = comments.stream()
                .filter(com -> com.getId() == id)
                .findFirst();

        opComment.ifPresent(comment -> comments.remove(comment));
    }

    public void removeAllComments() {

        comments.forEach(comment -> {
                    comment.setPost(null);
                });

        comments.clear();
    }
}
