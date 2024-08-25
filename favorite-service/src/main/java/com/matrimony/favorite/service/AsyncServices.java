package com.matrimony.favorite.service;

import com.matrimony.favorite.module.Favorite;
import com.matrimony.favorite.repository.FavoriteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Async("asyncTaskExecutor")
@Service
@Slf4j
public class AsyncServices {

    @Autowired
    private FavoriteRepository favoriteRepository;
 
    public void save(Favorite save) {
        log.debug("Async save Started!");
        log.debug(Thread.currentThread().getName());
        favoriteRepository.save(save);
        log.debug("Async save End!");
    }


    public void deleteByIdAndUsername(Long id, String username) {
        log.debug("Async deleteByIdAndUsername Started!");
        log.debug(Thread.currentThread().getName());
        favoriteRepository.deleteByIdAndUsername(id, username);
        log.debug("Async save end!");
    }
}
