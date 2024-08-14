package com.sr.authentication.model;

import com.sr.authentication.enums.Provider;
import com.sr.authentication.enums.Role;
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
    @Column(unique = true)
    private String userName;
    @Column(unique = true, length = 10)
    private String phoneNumber;
    private String emailId;
    private String password;
    private String fullName;
    private Date tokenGeneratorOn;
    private Date updateOn = new Date();
    private Date registerOn = new Date();
    private Provider provider;
    private boolean isActive;
    private String otp;
    private Date otpExpiryOn;
    private Role role;

}
