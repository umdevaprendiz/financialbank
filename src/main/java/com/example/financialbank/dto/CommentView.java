package com.example.financialbank.dto;

import java.time.LocalDateTime;

public record CommentView(Long id, String authorHandle, String text, LocalDateTime dateCreation) {}
