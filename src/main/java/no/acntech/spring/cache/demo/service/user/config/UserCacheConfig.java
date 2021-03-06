package no.acntech.spring.cache.demo.service.user.config;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class UserCacheConfig {

    public static final String USERS_CACHE = "Users";
    private static final Logger logger = LoggerFactory.getLogger(UserCacheConfig.class);

    @Bean
    public Cache usersCache() {
        return cacheManager().getCache(USERS_CACHE);
    }

    @Bean
    public CacheManager cacheManager() {
        logger.debug("Configuring Cache Manager");

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Collections.singletonList(new ConcurrentMapCache(USERS_CACHE)));
        cacheManager.initializeCaches();
        return cacheManager;
    }

    @Bean("appUsersInEnvKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new AppUsersInEnvKeyGenerator();
    }
}
