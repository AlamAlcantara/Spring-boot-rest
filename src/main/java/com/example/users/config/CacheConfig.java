/**
 * 
 */
package com.example.users.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author alamalcanta
 *
 */
@Configuration
@EnableCaching
public class CacheConfig {
	
	@Bean
	public CacheManager getCache(RedissonClient redissonClient) {
		Map<String, CacheConfig> config = new HashMap<>();
		config.put("users", new CacheConfig());
		
		return new RedissonSpringCacheManager(redissonClient);
	}
	
	//Reddis connection
	@Bean(destroyMethod = "shutdown")
	public RedissonClient redisson() {
		Config config = new Config();
		
		config.useSingleServer()
		.setAddress("redis://127.0.0.1:6379");
		
		return Redisson.create(config);
		
	}
}
