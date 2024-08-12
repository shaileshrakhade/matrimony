package com.matrimony.biodata.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "biodata")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BioData {
    @Id
    private String id;
    private String username;
    private String fullName;
    private boolean isApprove;
}
