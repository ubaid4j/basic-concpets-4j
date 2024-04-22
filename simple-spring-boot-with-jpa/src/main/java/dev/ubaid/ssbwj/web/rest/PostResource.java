package dev.ubaid.ssbwj.web.rest;

import dev.ubaid.ssbwj.service.PostService;
import dev.ubaid.ssbwj.service.dto.PostDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostResource {

    private final PostService postService;

    public PostResource(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PostDTO> findByUuid(@PathVariable String uuid) {
        return ResponseEntity.
                ok(postService.findByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAll() {
        return ResponseEntity
                .ok(postService.getAll());
    }

    @PostMapping
    public ResponseEntity<?> save(PostDTO postDTO) {
        return ResponseEntity
                .created(URI.create("/api/posts/" + postDTO.uuid()))
                .build();
    }
}
