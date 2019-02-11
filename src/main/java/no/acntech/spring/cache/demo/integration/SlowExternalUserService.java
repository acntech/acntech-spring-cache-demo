package no.acntech.spring.cache.demo.integration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import no.acntech.spring.cache.demo.domain.User;

import static no.acntech.spring.cache.demo.CachingConfig.USERS_CACHE;

@Service
public class SlowExternalUserService {

    private static final Logger logger = LoggerFactory.getLogger(SlowExternalUserService.class);
    private final long SLEEP_IN_SECONDS = 10;

    @Async
    @CachePut(value = USERS_CACHE)
    public CompletableFuture<List<User>> getUsers(List<String> namesForSlowService) {
        logger.debug("Calling Slow external User Service");

        LocalDateTime timeNow = LocalDateTime.now();
        List<User> users = namesForSlowService.stream().map(name -> new User(name, timeNow, "SlowExternalUserService")).collect(Collectors.toList());
        simulateSlowService();

        logger.debug("Returning list of users");

        return CompletableFuture.completedFuture(users);
    }

    private void simulateSlowService() {
        try {
            TimeUnit.SECONDS.sleep(SLEEP_IN_SECONDS);
        } catch (InterruptedException e) {
            // do nothing
        }
    }
}
