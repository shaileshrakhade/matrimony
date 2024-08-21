package com.matrimony.favorite.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteDao {
    private Long id;
    private String bioDataId;
    private String username;
    private String fullName;
    private String education;
    private String dob;
}