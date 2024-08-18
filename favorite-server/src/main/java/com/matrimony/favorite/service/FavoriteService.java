package com.matrimony.favorite.service;

import com.matrimony.favorite.customExceptions.exceptions.AlreadyAddedInFavorateException;
import com.matrimony.favorite.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.favorite.customExceptions.exceptions.FavoriteNotFoundException;
import com.matrimony.favorite.dao.FavoriteDao;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface FavoriteService {

    public  FavoriteDao add(FavoriteDao favoriteDao, HttpServletRequest request) throws AlreadyAddedInFavorateException, BioDataNotFoundException;
    public  FavoriteDao update(FavoriteDao favoriteDao , Long Id) throws FavoriteNotFoundException;
    public List<FavoriteDao> select(String username);
    public  long delete(Long Id,String username);

}
