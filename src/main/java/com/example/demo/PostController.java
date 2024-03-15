package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@RestController
@Tag(name = "posts", description = "게시글 API")
public class PostController {

    private final static List<Post> posts = new ArrayList<>();

    public PostController() {
        IntStream.range(0, 5)
                .forEach(i -> {
                    Post post = Post.builder()
                            .id(UUID.randomUUID())
                            .title("title" + i)
                            .content("content" + i)
                            .build();
                    posts.add(post);
                });
    }

    @Operation(summary = "게시글 전체조회")
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> findAll(@RequestParam("page") int page) {
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "게시글 생성")
    @PostMapping("/posts")
    public ResponseEntity<List<Post>> create(@RequestBody PostCreateForm form) {
        Post post = Post.builder()
                .id(UUID.randomUUID())
                .title(form.getTitle())
                .content(form.getContent())
                .build();
        posts.add(post);
        return ResponseEntity.created(URI.create(post.getId().toString())).build();
    }
}
