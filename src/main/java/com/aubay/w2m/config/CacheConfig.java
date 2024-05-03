package com.aubay.w2m.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aubay.w2m.util.W2MConstant;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	CacheManager cacheManager() {
		return new ConcurrentMapCacheManager(W2MConstant.CACHE_NAVES);
	}

}
