package com.matrimony.favorite.service;

import com.matrimony.favorite.customExceptions.exceptions.AlreadyAddedInFavorateException;
import com.matrimony.favorite.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.favorite.customExceptions.exceptions.FavoriteNotFoundException;
import com.matrimony.favorite.dao.FavoriteDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Feature;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public interface FavoriteService {

    public FavoriteDao add(FavoriteDao favoriteDao, HttpServletRequest request) throws AlreadyAddedInFavorateException, BioDataNotFoundException, ExecutionException, InterruptedException;

    public FavoriteDao update(FavoriteDao favoriteDao, Long Id) throws FavoriteNotFoundException;

    public List<FavoriteDao> select(String username);

    public void delete(Long Id, String username);

}
