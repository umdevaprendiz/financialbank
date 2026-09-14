package com.example.financialbank.dto;

import jakarta.validation.constraints.Size;

public record UpdateProfileDTO(
    @Size(max = 100) String displayName,
    @Size(max = 280) String bio,
    String avatarUrl
) {}
