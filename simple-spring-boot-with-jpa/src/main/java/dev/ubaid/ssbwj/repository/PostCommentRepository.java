package dev.ubaid.ssbwj.repository;

import dev.ubaid.ssbwj.domain.Post;
import dev.ubaid.ssbwj.domain.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    List<PostComment> findAllByPost(Post post);
}
