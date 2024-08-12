package com.matrimony.biodata.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BioDataDao {
    private String id;
    private String username;
    private String fullName;
    private boolean isApprove;
}
