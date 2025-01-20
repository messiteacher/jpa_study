package com.example.jpa.domain.post.post.service;

import com.example.jpa.domain.post.post.entity.Post;
import com.example.jpa.domain.post.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post write(String title, String body) {

        Post post = Post.builder()
                .title(title)
                .body(body)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

//        Post post = new Post(
//                null,
//                LocalDateTime.now(),
//                LocalDateTime.now(),
//                title,
//                body
//        );

//        post.setCreatedDate(LocalDateTime.now());
//        post.setModifiedDate(LocalDateTime.now());
//        post.setTitle(title);
//        post.setBody(body);

        return postRepository.save(post);
    }

    public long count() {
        return postRepository.count();
    }
}
