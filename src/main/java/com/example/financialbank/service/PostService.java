package com.example.financialbank.service;

import com.example.financialbank.dto.CommentView;
import com.example.financialbank.dto.CreateCommentDTO;
import com.example.financialbank.dto.CreatePostDTO;
import com.example.financialbank.dto.PostView;
import com.example.financialbank.model.Comment;
import com.example.financialbank.model.Post;
import com.example.financialbank.model.PostLike;
import com.example.financialbank.model.Profile;
import com.example.financialbank.model.User;
import com.example.financialbank.repository.CommentRepository;
import com.example.financialbank.repository.PostLikeRepository;
import com.example.financialbank.repository.PostRepository;
import com.example.financialbank.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostLikeRepository likeRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileService profileService;


    @Transactional
    public PostView createPost(User author, CreatePostDTO dto) {
        Profile profile = profileService.getOrCreate(author);
        Post post = new Post();
        post.setAuthor(profile);
        post.setUrlImage(dto.imageUrl());
        post.setCaption(dto.caption());
        return toView(postRepository.save(post), author);
    }

    public List<PostView> getFeed(User viewer) {
        return postRepository.findAllByOrderByDateCreationDesc().stream()
            .map(post -> toView(post, viewer))
            .toList();
    }

    public List<PostView> getPostsByHandle(String handle, User viewer) {
        Profile author = profileRepository.findByHandle(handle)
            .orElseThrow(() -> new NoSuchElementException("Perfil não encontrado: " + handle));
        return postRepository.findByAuthorOrderByDateCreationDesc(author).stream()
            .map(post -> toView(post, viewer))
            .toList();
    }

    @Transactional
    public void deletePost(User requester, Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new NoSuchElementException("Post não encontrado"));
        if (!post.getAuthor().getUser().getId().equals(requester.getId())) {
            throw new SecurityException("Você só pode apagar os próprios posts.");
        }
        postRepository.delete(post);
    }

    @Transactional
    public void toggleLike(User user, Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new NoSuchElementException("Post não encontrado"));
        likeRepository.findByPostAndUser(post, user).ifPresentOrElse(
            likeRepository::delete,
            () -> {
                PostLike like = new PostLike();
                like.setPost(post);
                like.setUser(user);
                likeRepository.save(like);
            });
    }

    @Transactional
    public CommentView addComment(User author, Long postId, CreateCommentDTO dto) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new NoSuchElementException("Post não encontrado"));
        Profile authorProfile = profileService.getOrCreate(author);
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setAuthor(authorProfile);
        comment.setText(dto.text());
        Comment saved = commentRepository.save(comment);
        return new CommentView(saved.getId(), authorProfile.getHandle(), saved.getText(), saved.getDateCreation());
    }

    public List<CommentView> getComments(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new NoSuchElementException("Post não encontrado"));
        return commentRepository.findByPostOrderByDateCreationAsc(post).stream()
            .map(c -> new CommentView(c.getId(), c.getAuthor().getHandle(), c.getText(), c.getDateCreation()))
            .toList();
    }

    private PostView toView(Post post, User viewer) {
        long likeCount = likeRepository.countByPost(post);
        long commentCount = commentRepository.countByPost(post);
        boolean likedByMe = likeRepository.existsByPostAndUser(post, viewer);
        Profile author = post.getAuthor();
        return new PostView(post.getId(), author.getHandle(), author.getAvatarUrl(), post.getUrlImage(),
            post.getCaption(), post.getDateCreation(), likeCount, likedByMe, commentCount);
    }
}
