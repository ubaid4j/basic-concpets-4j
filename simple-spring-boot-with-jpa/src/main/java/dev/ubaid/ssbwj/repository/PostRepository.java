package dev.ubaid.ssbwj.repository;

import dev.ubaid.ssbwj.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
 
    Optional<Post> findByUuid(String uuid);
}
