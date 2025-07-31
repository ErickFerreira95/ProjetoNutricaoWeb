package com.mycompany.ProjetoNutricaoWeb.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/css/**", "/js/**", "/images/**",
                                "/login", "/fazerLogin", "/criarUsuario", "/salvarUsuario",
                                "/redefinirSenha", "/salvarNovaSenha", "/cadastroAlimentos"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin().disable() // ← IMPORTANTE: desativa o login automático do Spring
                .csrf().disable()      // ← se você estiver usando POST sem token CSRF (ex: formulário manual)
                .logout(logout -> logout.permitAll());

        return http.build();
    }
}

