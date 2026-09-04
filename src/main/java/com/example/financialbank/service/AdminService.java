package com.example.financialbank.service;

import com.example.financialbank.model.User;
import com.example.financialbank.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.financialbank.dto.UserUpdateDTO;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;


    //Listar os usuários
    public List<User> listarUsuarios() {
        return repository.findAll();
    }

    //BUSCA USUÁRIO PELO ID
    public User buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Usuário não encontrado"));
    }

    //editarUser
    public User editarUsuario(Long id, UserUpdateDTO dto) {
        User user = buscarPorId(id);

        if (dto.getNome() != null && !dto.getNome().isBlank()) {
            user.setNome(dto.getNome());
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {

            boolean emailExiste = repository.existsByEmail(dto.getEmail());

            if (emailExiste && !user.getEmail().equals(dto.getEmail())) {
                throw new IllegalArgumentException("Email já está em uso");
            }

            user.setEmail(dto.getEmail());
        }

        //resolver problema da injeção do encoder...
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            user.setSenha(encoder.encode(dto.getSenha()));
        }

        user.setAtualizadoEm(LocalDateTime.now());

        return repository.save(user);
    }

    //bloquear um usuário.
    public User bloquearUsuario(Long id) {
        User user = buscarPorId(id);

        user.setAtivo(false);

        return repository.save(user);
    }

    //desbloquear um usuário.
    public User desbloquearUsuario(Long id) {
        User user = buscarPorId(id);

        user.setAtivo(true);

        return repository.save(user);
    }

    public void deletarUsuario(Long id) {
        User user = buscarPorId(id);

        repository.delete(user);
    }

}




