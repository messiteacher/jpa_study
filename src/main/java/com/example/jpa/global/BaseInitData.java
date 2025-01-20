package com.example.jpa.global;

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
            Post p2 = postService.write("title2", "body2");
            Post p3 = postService.write("title3", "body3");
        };
    }

    @Bean
    @Order(2)
    public ApplicationRunner applicationRunner2() {

        return new ApplicationRunner() {
            @Override
            @Transactional
            public void run(ApplicationArguments args) throws Exception {

                Post post = postService.findById(1L).get();
                Thread.sleep(1000);
                postService.modify(post, "new title", "new body");
            }
        };
    }

    @Bean
    @Order(3)
    public ApplicationRunner applicationRunner3() {

        return new ApplicationRunner() {
            @Override
            @Transactional
            public void run(ApplicationArguments args) throws Exception {

                Post p1 = postService.findById(1L).get();
                Post p2 = postService.findById(2L).get();

                System.out.println("===== p1 삭제 =====");
                postService.delete(p1);
                System.out.println("===== p1 삭제 완료 =====");

                System.out.println("===== p2 삭제 =====");
                postService.delete(p2);
                System.out.println("===== p2 삭제 완료 =====");

//                postService.deleteById(1L);
//                postService.deleteById(2L);
            }
        };
    }
}
