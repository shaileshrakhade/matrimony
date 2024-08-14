package com.matrimony.admin.dao;

import com.matrimony.admin.model.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminDao {
    private String id;
    private String key;
    private String value;

    public AdminDao(Admin admin) {
        id = admin.getId();
        key = admin.getKey();
        value = admin.getValue();
    }
}
