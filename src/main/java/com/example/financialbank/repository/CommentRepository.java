package com.example.financialbank.repository;

import com.example.financialbank.model.Comment;
import com.example.financialbank.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostOrderByDateCreationAsc(Post post);
    long countByPost(Post post);
}
