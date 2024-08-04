package com.lagn.authentication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true,length =10)
    private String phoneNumber;
    private String password;
    private String name;
    @Column(unique = true,nullable = false)
    private String email;
    private String whatsAppNo;
    @ColumnDefault("True")
    private boolean isActive;
    private Date lastlogin;
    private String birthdate;
    private String address;
    private String fullName;
    private String gender;



}
