package com.dm.ticket.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public KeyGenerator cacheKeyGenerator(){
        return new CacheKeyGenerator();

    }
}
