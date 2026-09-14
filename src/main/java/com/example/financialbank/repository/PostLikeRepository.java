package com.example.financialbank.repository;

import com.example.financialbank.model.Post;
import com.example.financialbank.model.PostLike;
import com.example.financialbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    long countByPost(Post post);
    Optional<PostLike> findByPostAndUser(Post post, User user);
    boolean existsByPostAndUser(Post post, User user);
}
