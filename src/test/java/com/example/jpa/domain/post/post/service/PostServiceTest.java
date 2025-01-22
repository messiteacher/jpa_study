package com.example.jpa.domain.post.post.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@SpringBootTest
@Profile("test")
public class PostServiceTest {

    @Test
    public void test() {
        System.out.println("test");
    }
}
