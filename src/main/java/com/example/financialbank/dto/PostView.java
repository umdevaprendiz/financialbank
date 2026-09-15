package com.example.financialbank.dto;

import java.time.LocalDateTime;

public record PostView(
    Long id,
    String authorHandle,
    String authorAvatarUrl,
    String imageUrl,
    String caption,
    LocalDateTime dateCreation,
    long likeCount,
    boolean likedByMe,
    long commentCount
) {}
