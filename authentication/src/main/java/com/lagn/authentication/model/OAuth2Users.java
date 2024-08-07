//package com.lagn.authentication.model;
//
//import com.lagn.authentication.enums.Provider;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import java.util.Date;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Builder
//@Table(name = "oauthusers")
//public class OAuth2Users {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//    private String name;
//    @Column(unique = true, nullable = false)
//    private String email;
//    @Enumerated(EnumType.STRING)
//    private Provider provider;
//    private Date lastlogin;
//    private String birthdate;
////    @OneToOne(cascade = CascadeType.ALL)
//////    @JoinColumn(name = "address_id", referencedColumnName = "id")
////    private Address address;
//    private String fullName;
//    private String gender;
//    private String phoneNumber;
//}
