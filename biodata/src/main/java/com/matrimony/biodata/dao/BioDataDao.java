package com.matrimony.biodata.dao;

import com.matrimony.biodata.model.BioData;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BioDataDao {
    private String id;
    private String fullName;
    private boolean isApprove;

}
