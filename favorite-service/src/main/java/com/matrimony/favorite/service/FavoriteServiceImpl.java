package com.matrimony.favorite.service;

import com.matrimony.favorite.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.favorite.customExceptions.exceptions.FavoriteNotFoundException;
import com.matrimony.favorite.dao.FavoriteDao;
import com.matrimony.favorite.module.Favorite;
import com.matrimony.favorite.repository.FavoriteRepository;
import io.micrometer.observation.ObservationRegistry;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Service
@Slf4j
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private ObservationRegistry observationRegistry;
    @Autowired
    private BiodataService biodataService;
    @Autowired
    private AsyncServices asyncServices;

    @Override
    public FavoriteDao add(FavoriteDao favoriteDao, HttpServletRequest request) throws BioDataNotFoundException, ExecutionException, InterruptedException {
        log.debug(Thread.currentThread().getName());
        if (biodataService.isPresent(request, favoriteDao.getBioDataId())) {
            Favorite favorite = Favorite.builder()
                    .bioDataId(favoriteDao.getBioDataId())
                    .username(favoriteDao.getUsername())
                    .fullName(favoriteDao.getFullName())
                    .education(favoriteDao.getEducation())
                    .dob(favoriteDao.getDob())
                    .build();

            Optional<Favorite> favoriteOptional = favoriteRepository.findByBioDataIdAndUsername(favoriteDao.getBioDataId(), favoriteDao.getUsername());
            favoriteOptional.ifPresent(value -> favorite.setId(value.getId()));
            //Async save method call
            log.debug(Thread.currentThread().getName());
            log.debug("call Async save from add!");
            asyncServices.save(favorite);
            return FavoriteDao.builder()
                    .id(favorite.getId())
                    .bioDataId(favorite.getBioDataId())
                    .username(favorite.getUsername())
                    .fullName(favorite.getFullName())
                    .education(favorite.getEducation())
                    .dob(favorite.getDob())
                    .build();
        } else {
            throw new BioDataNotFoundException("Biodata is not Exist");
        }


    }

    @Override
    public FavoriteDao update(FavoriteDao favoriteDao, Long id) throws FavoriteNotFoundException {
        Favorite favorite = favoriteRepository.findByBioDataIdAndUsername(favoriteDao.getBioDataId(), favoriteDao.getUsername()).orElseThrow(() -> new FavoriteNotFoundException("Favorite Not found"));

        favorite.setFullName(favoriteDao.getFullName());
        favorite.setEducation(favoriteDao.getEducation());
        favorite.setDob(favoriteDao.getDob());

        log.debug(Thread.currentThread().getName());
        log.debug("call Async save from update!");
        asyncServices.save(favorite);
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
        return favoriteRepository.findByUsername(username).stream().map(favorite ->
                {
                    return FavoriteDao.builder()
                            .id(favorite.getId())
                            .bioDataId(favorite.getBioDataId())
                            .username(favorite.getUsername())
                            .fullName(favorite.getFullName())
                            .education(favorite.getEducation())
                            .dob(favorite.getDob())
                            .build();
                }
        ).toList();
    }

    @Override
    @Transactional
    public void delete(Long id, String username) {
        log.debug(Thread.currentThread().getName());
        log.debug("call Async save from delete!");
        asyncServices.deleteByIdAndUsername(id, username);
    }
}