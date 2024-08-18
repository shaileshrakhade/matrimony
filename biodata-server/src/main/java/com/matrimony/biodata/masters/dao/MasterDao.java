package com.matrimony.biodata.masters.dao;

import com.matrimony.biodata.masters.model.Master;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MasterDao {
    private String id;
    private String key;
    private String value;

    public MasterDao(Master master) {
        id = master.getId();
        key = master.getKey();
        value = master.getValue();
    }
}