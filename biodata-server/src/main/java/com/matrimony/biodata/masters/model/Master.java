package com.matrimony.biodata.masters.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "master")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Master {
    @Id
    private String id;
    private String key;
    private String value;

}