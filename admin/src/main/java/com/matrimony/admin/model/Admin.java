package com.matrimony.admin.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "admin")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Admin {
    @Id
    private String id;
    private String key;
    private String value;

}
