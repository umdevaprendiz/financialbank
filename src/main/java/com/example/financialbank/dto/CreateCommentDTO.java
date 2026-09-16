package com.example.financialbank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentDTO(
    @NotBlank @Size(max = 300) String text
) {}
