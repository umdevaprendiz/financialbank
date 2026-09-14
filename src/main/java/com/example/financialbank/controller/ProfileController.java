package com.example.financialbank.controller;

import com.example.financialbank.dto.ProfileView;
import com.example.financialbank.dto.UpdateProfileDTO;
import com.example.financialbank.model.User;
import com.example.financialbank.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/social/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/me")
    public ProfileView me(@AuthenticationPrincipal User user) {
        return profileService.getMine(user);
    }

    @PutMapping("/me")
    public ProfileView update(@AuthenticationPrincipal User user, @Valid @RequestBody UpdateProfileDTO dto) {
        return profileService.update(user, dto);
    }

    @GetMapping("/{handle}")
    public ProfileView byHandle(@PathVariable String handle) {
        return profileService.getByHandle(handle);
    }
}
