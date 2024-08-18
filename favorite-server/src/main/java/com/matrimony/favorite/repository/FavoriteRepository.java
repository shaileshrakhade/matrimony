package com.matrimony.favorite.repository;

import com.matrimony.favorite.dao.FavoriteDao;
import com.matrimony.favorite.module.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByBioDataIdAndUsername(String bioDataId, String username);

    List<Favorite> findByUsername(String username);

    long deleteByIdAndUsername(Long id, String username);
}
