package com.matrimony.favorite.service;

import com.matrimony.favorite.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.favorite.customExceptions.exceptions.FavoriteNotFoundException;
import com.matrimony.favorite.dao.FavoriteDao;
import com.matrimony.favorite.module.Favorite;
import com.matrimony.favorite.repository.FavoriteRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ObservationRegistry observationRegistry;
    private final WebClient.Builder webClientBuilder;

    @Override
    public FavoriteDao add(FavoriteDao favoriteDao, HttpServletRequest request) throws BioDataNotFoundException {
        boolean isPresent = false;
        // Call Inventory Service, and place order if product is in stock
        Observation inventoryServiceObservation = Observation.createNotStarted("BIODATA-SERVICE-LOOKUP",
                this.observationRegistry);
        log.info("Calling the BIODATA-SERVICE");
        inventoryServiceObservation.lowCardinalityKeyValue("call", "BIODATA-SERVICE");
        //inventoryServiceObservation is used to maintain the same SPAN ID to tract it's single request
        isPresent = Boolean.TRUE.equals(inventoryServiceObservation.observe(() -> {
            return webClientBuilder.build()
                    .get()
                    .uri("http://BIODATA-SERVICE/bio-data/is-present/" + favoriteDao.getBioDataId())
//                    .header(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION))
                    .retrieve().bodyToMono(Boolean.class).block();
        }));
        if (isPresent) {
            Favorite favorite = Favorite.builder()
                    .bioDataId(favoriteDao.getBioDataId())
                    .username(favoriteDao.getUsername())
                    .fullName(favoriteDao.getFullName())
                    .education(favoriteDao.getEducation())
                    .dob(favoriteDao.getDob())
                    .build();

            Optional<Favorite> favoriteOptional = favoriteRepository.findByBioDataIdAndUsername(favoriteDao.getBioDataId(), favoriteDao.getUsername());
            if (favoriteOptional.isPresent())
                favorite.setId(favoriteOptional.get().getId());

            favorite = favoriteRepository.save(favorite);
            return FavoriteDao.builder()
                    .id(favorite.getId())
                    .bioDataId(favorite.getBioDataId())
                    .username(favorite.getUsername())
                    .fullName(favorite.getFullName())
                    .education(favorite.getEducation())
                    .dob(favorite.getDob())
                    .build();
        } else {
            try {
                throw new BioDataNotFoundException("Biodata is not Exist");
            } catch (BioDataNotFoundException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @Override
    public FavoriteDao update(FavoriteDao favoriteDao, Long id) throws FavoriteNotFoundException {
        Favorite favorite = favoriteRepository.findByBioDataIdAndUsername(favoriteDao.getBioDataId(), favoriteDao.getUsername()).orElseThrow(() -> new FavoriteNotFoundException("Favorite Not found"));

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
    public long delete(Long id, String username) {
        return favoriteRepository.deleteByIdAndUsername(id, username);
    }
}