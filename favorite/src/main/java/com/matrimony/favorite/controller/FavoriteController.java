package com.matrimony.favorite.controller;

import com.matrimony.favorite.dao.FavoriteDao;
import com.matrimony.favorite.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorite/")
@RequiredArgsConstructor
@Slf4j
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("add")
    public FavoriteDao add(@RequestBody FavoriteDao favoriteDao) {
        return favoriteService.add(favoriteDao);
    }

    @PutMapping("update/{id}")
    public FavoriteDao update(@RequestBody FavoriteDao favoriteDao,@PathVariable("id") long id) {
        return favoriteService.update(favoriteDao, id);
    }
    @PutMapping("update")
    public FavoriteDao updateRequestPara(@RequestBody FavoriteDao favoriteDao,@RequestParam("id") long id) {
        return favoriteService.update(favoriteDao, id);
    }

    @GetMapping("select/{username}")
    public List<FavoriteDao> select(@PathVariable String username) {
        return favoriteService.select(username);
    }

    @DeleteMapping("delete/{id}/{username}")
    public long delete(@PathVariable long id, @PathVariable String username) {
        return favoriteService.delete(id, username);
    }

}


