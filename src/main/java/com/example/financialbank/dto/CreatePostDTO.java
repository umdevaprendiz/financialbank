package com.example.financialbank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePostDTO(
    @NotBlank String imageUrl,
    @Size(max = 500) String caption
) {}
