package com.lagn.authentication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.core.oidc.AddressStandardClaim;


//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Table(name = "address")
public class Address{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String formatted;
    private  String streetAddress;
    private  String locality;
    private  String region;
    private  String postalCode;
    private  String country;

}
