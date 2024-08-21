package com.sr.authentication.service;

import com.sr.authentication.model.Users;
import com.sr.authentication.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
@Async("asyncTaskExecutor")
public class AsyncServices {

    private final UserRepository userRepository;

    public void save(Users user) {
        log.debug("Async save End!");
        userRepository.save(user);
        log.debug("Async save Started!");
    }

    public void deleteByIdAndUserName(Long userId, String username) {
        log.debug("Async save End!");
        userRepository.deleteByIdAndUserName(userId, username);
        log.debug("Async save Started!");
    }
}
