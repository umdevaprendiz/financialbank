package com.example.financialbank.service;

import com.example.financialbank.dto.ProfileView;
import com.example.financialbank.dto.UpdateProfileDTO;
import com.example.financialbank.model.Profile;
import com.example.financialbank.model.User;
import com.example.financialbank.repository.PostRepository;
import com.example.financialbank.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final PostRepository postRepository;

    public ProfileService(ProfileRepository profileRepository, PostRepository postRepository) {
        this.profileRepository = profileRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public Profile getOrCreate(User user) {
        return profileRepository.findByUser(user).orElseGet(() -> {
            Profile p = new Profile();
            p.setUser(user);
            p.setHandle(generateHandleFromEmail(user.getEmail()));
            p.setDisplayName(user.getNome());
            return profileRepository.save(p);
        });
    }

    public ProfileView getByHandle(String handle) {
        Profile profile = profileRepository.findByHandle(handle)
            .orElseThrow(() -> new NoSuchElementException("Perfil não encontrado: " + handle));
        long postCount = postRepository.findByAuthorOrderByDateCreationDesc(profile).size();
        return toView(profile, postCount);
    }

    public ProfileView getMine(User user) {
        Profile profile = getOrCreate(user);
        long postCount = postRepository.findByAuthorOrderByDateCreationDesc(profile).size();
        return toView(profile, postCount);
    }

    @Transactional
    public ProfileView update(User user, UpdateProfileDTO dto) {
        Profile profile = getOrCreate(user);
        if (dto.displayName() != null) profile.setDisplayName(dto.displayName());
        if (dto.bio() != null) profile.setBio(dto.bio());
        if (dto.avatarUrl() != null) profile.setAvatarUrl(dto.avatarUrl());
        Profile saved = profileRepository.save(profile);
        long postCount = postRepository.findByAuthorOrderByDateCreationDesc(saved).size();
        return toView(saved, postCount);
    }

    private String generateHandleFromEmail(String email) {
        String base = email.split("@")[0].toLowerCase().replaceAll("[^a-z0-9]", "");
        String handle = base.isBlank() ? "usuario" : base;
        String candidate = handle;
        int suffix = 1;
        while (profileRepository.existsByHandle(candidate)) {
            candidate = handle + suffix++;
        }
        return candidate;
    }

    private ProfileView toView(Profile profile, long postCount) {
        return new ProfileView(profile.getHandle(), profile.getDisplayName(), profile.getBio(), profile.getAvatarUrl(), postCount);
    }
}
