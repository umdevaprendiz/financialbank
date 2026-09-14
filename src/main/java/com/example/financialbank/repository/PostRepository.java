package com.example.financialbank.repository;

import com.example.financialbank.model.Post;
import com.example.financialbank.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByDateCreationDesc();
    List<Post> findByAuthorOrderByDateCreationDesc(Profile author);
}
