package com.example.financialbank.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.example.financialbank.configuration.Permission.*;
import static com.example.financialbank.configuration.Role.ADMIN;
import static com.example.financialbank.configuration.Role.MANAGER;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    //Colocando SecurityFilterChain no aplicativo
    public SecurityFilterChain securityFilterChain(HttpSecurity  http) throws Exception{
        http //aplicamos o método http
                .csrf(c ->  c.disable()) //colocamos o .csrf como desabilitado para os clientes acessarem o httpRequest(awuth - > auth, que passa o requestMatchers(páginas liberadas)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/cadastro","/auth/register","/cadastroStyle.css")
                        .permitAll()
                        .requestMatchers("/api/financial/admin/**").hasAnyRole(ADMIN.name(), MANAGER.name())

 //Cada autoridade(Role) tem sua permissão (permission), e cada um possui sua rota, porém aquele com mais hierarquia possui mais permissões
                                .requestMatchers("/api/financial/admin/**").hasRole(ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/financial/admin/**").hasAnyAuthority(ADMIN_READ.name())
                        .requestMatchers(HttpMethod.POST, "/api/financial/admin/**").hasAnyAuthority(ADMIN_CREATE.name())
                        .requestMatchers(HttpMethod.PUT, "/api/financial/admin/**").hasAnyAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/financial/admin/**").hasAnyAuthority(ADMIN_DELETE.name())

                                //promover alguém à admin ou management
                                .requestMatchers(HttpMethod.PUT, "/users/{id}/promote/**").hasAnyAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(HttpMethod.GET, "/api/financial/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                        .requestMatchers(HttpMethod.POST, "/api/financial/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_READ.name())
                        .requestMatchers(HttpMethod.PUT, "/api/financial/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_READ.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/financial/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_READ.name())


                        .anyRequest()
                        .authenticated()

                );
        
              return http.build();
    }



}
