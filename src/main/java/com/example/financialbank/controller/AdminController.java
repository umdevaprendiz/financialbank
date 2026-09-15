package com.example.financialbank.controller;



import com.example.financialbank.dto.DashboardDTO;
import com.example.financialbank.dto.UserUpdateDTO;
import com.example.financialbank.model.User;
import com.example.financialbank.repository.UserRepository;
import com.example.financialbank.service.AdminService;
import com.example.financialbank.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService service;
    private final UserRepository repository;
    private final DashboardService dashboardService;

    public AdminController(AdminService service, UserRepository repository, DashboardService dashboardService){
        this.repository = repository;
        this.service = service;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> listarUsuarios(){
        return ResponseEntity.ok(service.listarUsuarios());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
       service.deletarUsuario(id);
       return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> editarUsuario(@PathVariable Long id, @RequestBody UserUpdateDTO DTO){
        return ResponseEntity.ok(service.editarUsuario(id, DTO));
    }

    @PatchMapping("/user/{id}/bloquear")
    public ResponseEntity<User> bloquearUsuario(@PathVariable Long id){
        return ResponseEntity.ok(service.bloquearUsuario(id));
    }

    @PatchMapping("/user/{id}/desbloquear")
    public ResponseEntity<User> desbloquearUsuario(@PathVariable Long id){
        return ResponseEntity.ok(service.desbloquearUsuario(id));
    }

    @GetMapping("/dashboard")
    public String montarDashboard(Model model){
       model.addAttribute("dashboard", dashboardService.montarDashboard());
       return "adminpage/adminPage";
    }


}
