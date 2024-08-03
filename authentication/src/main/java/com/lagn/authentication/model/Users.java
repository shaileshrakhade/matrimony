package com.lagn.authentication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.processing.Pattern;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true,length =10,nullable = false)
    private String mobile;
    @Column(nullable = false)
    private String password;
    private String name;
    private String email;
    private String whatsAppNo;
    @ColumnDefault("True")
    private boolean isActive;

}
