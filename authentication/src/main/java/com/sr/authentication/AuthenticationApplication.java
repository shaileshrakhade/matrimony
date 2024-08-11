package com.sr.authentication;

import com.sr.authentication.enums.Provider;
import com.sr.authentication.enums.Role;
import com.sr.authentication.model.Users;
import com.sr.authentication.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;

@SpringBootApplication
@EnableAsync
public class AuthenticationApplication {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
    }

    @Bean
    public String defaultAdminRecord() {
        Users users = new Users();
        users.setUserName("admin");
        users.setPassword(passwordEncoder.encode("admin"));
        users.setProvider(Provider.LOCAL);
        users.setFullName("admin");
        users.setEmailId("shailesh.sr@gmail.com");
        users.setActive(true);
        users.setRole(Role.ADMIN);
        try {
            userRepository.save(users);
            return "default Admin USERNAME:admin & PASSWORD:admin";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return "";
    }
}
