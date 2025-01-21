package com.example.jpa.global;

import com.example.jpa.domain.post.comment.entity.Comment;
import com.example.jpa.domain.post.comment.service.CommentService;
import com.example.jpa.domain.post.post.entity.Post;
import com.example.jpa.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {

    private final PostService postService;
    private final CommentService commentService;

    @Bean
    @Order(1)
    public ApplicationRunner applicationRunner() {

        return args -> {

            // 샘플 데이터 3개 생성.
            // 데이터 3개가 이미 있으면 패스
            if (postService.count()  > 0) {
                return ;
            }

            Post p1 = postService.write("title1", "body1");
            postService.write("title2", "body2");
            postService.write("title3", "body3");

            commentService.write(p1, "comment1");
            commentService.write(p1, "comment2");
            commentService.write(p1, "comment3");
        };
    }

    @Bean
    @Order(2)
    public ApplicationRunner applicationRunner2() {

        return new ApplicationRunner() {
            @Override
            @Transactional
            public void run(ApplicationArguments args) throws Exception {

                Comment c1 = commentService.findById(1L).get();

                Post post = c1.getPost();

                System.out.println(post.getId());
                System.out.println(post.getTitle());
            }
        };
    }
}
