package no.acntech.spring.cache.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import no.acntech.spring.cache.demo.domain.User;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserCacheProcessService userCacheProcessService;

    public UserService(UserCacheProcessService userCacheProcessService) {
        this.userCacheProcessService = userCacheProcessService;
    }

    public List<User> getUsers(List<String> namesForSlowService, List<String> namesForSuperSlowService) {
        List<User> users = userCacheProcessService.getUsersFromCache(namesForSlowService, namesForSuperSlowService);
        logger.debug("Returning {} users from cache", users.size());
        return users;
    }

}
