package com.pani.melichallenge.config;

import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@EnableCaching
@Configuration
public class CacheConfig {
	private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

	@Bean
	public CacheManager cacheManager() {
		ConcurrentMapCacheManager manager = new ConcurrentMapCacheManager();
		manager.setCacheNames(Arrays.asList("countries"));
		logger.info("Using ConcurrentMapCacheManager as cache");
		return manager;
	}

	@CacheEvict(allEntries = true, value = { "countries" })
	@Scheduled(fixedDelay = 10 * 60 * 1000, initialDelay = 500)
	public void reportCacheEvict() {
		logger.info("Evict of 'countries' cache performed at: {}", new Date().toString());
	}

}