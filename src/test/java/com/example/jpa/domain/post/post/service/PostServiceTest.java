package com.example.jpa.domain.post.post.service;

import com.example.jpa.domain.post.post.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    @DisplayName("글 2개 작성")
    @Transactional
    @Rollback(false)
    public void test() {

        postService.write("title1", "body1");
        postService.write("title2", "body2");
    }

    @Test
    @DisplayName("모든 글 조회")
    @Transactional
    public void t2() {

        List<Post> all = this.postService.findAll();
        assertEquals(3, all.size());

        Post post = all.get(0);
        assertEquals("title1", post.getTitle());
    }

    @Test
    @DisplayName("모든 글 조회")
    @Transactional
    public void t3() {

        Optional<Post> opPost = postService.findById(1);

        if (opPost.isPresent()) {
            assertThat(opPost.get().getTitle()).isEqualTo("title1");
        }
    }

    @Test
    @DisplayName("제목으로 검색")
    @Transactional
    public void t4() {

        List<Post> posts = postService.findByTitle("title1");

        assertThat(posts.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("제목과 내용으로 검색")
    @Transactional
    public void t5() {

        List<Post> posts = postService.findByTitleAndBody("title1", "body1");
        
        assertThat(posts.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("제목이 포함된 결과 조회")
    @Transactional
    public void t6() {

        List<Post> posts = postService.findByTitleLike("title%");

        assertThat(posts.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("아이디 순으로 내림차순 정렬")
    @Transactional
    public void t7() {

        List<Post> posts = postService.findByOrderByIdDesc();

        assertThat(posts.size()).isEqualTo(3);
        assertThat(posts.get(0).getId()).isEqualTo(3);
    }

    @Test
    @DisplayName("위에서 2개만 조회")
    @Transactional
    public void t8() {

        List<Post> posts = postService.findTop2ByTitleOrderByIdDesc("title1");

        assertThat(posts.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("findAll(Pageable pageable)")
    void t11() {

        int itemsPerPage = 2; // 한 페이지에 보여줄 아이템 수
        int pageNumber = 2; // 현재 페이지 == 2
        pageNumber--; // 1을 빼는 이유는 jpa는 페이지 번호를 0부터 시작하기 때문
        Pageable pageable = PageRequest.of(pageNumber, itemsPerPage, Sort.by(Sort.Direction.DESC, "id"));
        Page<Post> postPage = postService.findAll(pageable);
        List<Post> posts = postPage.getContent();
        assertEquals(1, posts.size()); // 글이 총 3개이고, 현재 페이지는 2이므로 1개만 보여야 함
        Post post = posts.get(0);
        assertEquals(1, post.getId());
        assertEquals("title1", post.getTitle());
        assertEquals(3, postPage.getTotalElements()); // 전체 글 수
        assertEquals(2, postPage.getTotalPages()); // 전체 페이지 수
        assertEquals(1, postPage.getNumberOfElements()); // 현재 페이지에 노출된 글 수
        assertEquals(pageNumber, postPage.getNumber()); // 현재 페이지 번호
    }
}
