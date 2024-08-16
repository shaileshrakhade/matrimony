package com.matrimony.favorite.service;

import com.matrimony.favorite.customExceptions.exceptions.AlreadyAddedInFavorateException;
import com.matrimony.favorite.customExceptions.exceptions.FavoriteNotFoundException;
import com.matrimony.favorite.dao.FavoriteDao;
import com.matrimony.favorite.module.Favorite;
import com.matrimony.favorite.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoriteRepository favoriteRepository;

    @Override
    public FavoriteDao add(FavoriteDao favoriteDao) throws AlreadyAddedInFavorateException {
        if (favoriteRepository.findByBioDataIdAndUsername(favoriteDao.getBioDataId(), favoriteDao.getUsername()).isPresent())
            throw new AlreadyAddedInFavorateException("Bio-data is already in favorite");


        Favorite favorite = Favorite.builder()
                .bioDataId(favoriteDao.getBioDataId())
                .username(favoriteDao.getUsername())
                .fullName(favoriteDao.getFullName())
                .education(favoriteDao.getEducation())
                .dob(favoriteDao.getDob())
                .build();

        favorite = favoriteRepository.save(favorite);
        return FavoriteDao.builder()
                .id(favorite.getId())
                .bioDataId(favorite.getBioDataId())
                .username(favorite.getUsername())
                .fullName(favorite.getFullName())
                .education(favorite.getEducation())
                .dob(favorite.getDob())
                .build();
    }

    @Override
    public FavoriteDao update(FavoriteDao favoriteDao, Long id) throws FavoriteNotFoundException {
        Favorite favorite = favoriteRepository.findById(id).orElseThrow(() -> new FavoriteNotFoundException("Favorite Not found"));


        favorite.setBioDataId(favoriteDao.getBioDataId());
        favorite.setUsername(favoriteDao.getUsername());
        favorite.setFullName(favoriteDao.getFullName());
        favorite.setEducation(favoriteDao.getEducation());
        favorite.setDob(favoriteDao.getDob());

        favorite = favoriteRepository.save(favorite);
        return FavoriteDao.builder()
                .id(favorite.getId())
                .bioDataId(favorite.getBioDataId())
                .username(favorite.getUsername())
                .fullName(favorite.getFullName())
                .education(favorite.getEducation())
                .dob(favorite.getDob())
                .build();
    }

    @Override
    public List<FavoriteDao> select(String username) {
        return List.of();
    }

    @Override
    public long delete(Long Id, String username) {
        return 0;
    }
}