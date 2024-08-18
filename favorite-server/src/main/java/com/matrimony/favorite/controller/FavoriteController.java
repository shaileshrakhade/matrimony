package com.matrimony.favorite.controller;

import com.matrimony.favorite.customExceptions.exceptions.AlreadyAddedInFavorateException;

import com.matrimony.favorite.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.favorite.dao.FavoriteDao;
import com.matrimony.favorite.service.FavoriteService;
import com.matrimony.favorite.util.jwt.JwtClaims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorite/")
@RequiredArgsConstructor
@Slf4j
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private JwtClaims jwtClaims;


    @PostMapping("add")
    public FavoriteDao add(HttpServletRequest request, @RequestBody FavoriteDao favoriteDao) throws AlreadyAddedInFavorateException, BioDataNotFoundException {
        String username = jwtClaims.extractUsername(request.getHeader(HttpHeaders.AUTHORIZATION));
        favoriteDao.setUsername(username);
        return favoriteService.add(favoriteDao,request);
    }

    @GetMapping("show")
    public List<FavoriteDao> select(HttpServletRequest request) {
        String username = jwtClaims.extractUsername(request.getHeader(HttpHeaders.AUTHORIZATION));
        return favoriteService.select(username);
    }

    @DeleteMapping("delete/{id}")
    public long delete(HttpServletRequest request, @PathVariable long id) {
        String username = jwtClaims.extractUsername(request.getHeader(HttpHeaders.AUTHORIZATION));
        return favoriteService.delete(id, username);
    }

}


