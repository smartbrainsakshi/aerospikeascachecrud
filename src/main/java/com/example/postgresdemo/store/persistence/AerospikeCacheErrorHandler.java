package com.example.postgresdemo.store.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

public class AerospikeCacheErrorHandler implements CacheErrorHandler {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
		log.warn("handleCacheGetError"
				+ " cache name : " + cache.getName()
				+ " key : " + key.toString()
				+ " error : " + exception.getLocalizedMessage());
	}

	@Override
	public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
		log.warn("handleCachePutError"
				+ " cache name : " + cache.getName()
				+ " key : " + key.toString()
				+ " error : " + exception.getLocalizedMessage());
	}

	@Override
	public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
		log.warn("handleCacheEvictError"
				+ " cache name : " + cache.getName()
				+ " key : " + key.toString()
				+ " error : " + exception.getLocalizedMessage());		
	}

	@Override
	public void handleCacheClearError(RuntimeException exception, Cache cache) {
		log.warn("handleCacheClearError"
				+ " cache name : " + cache.getName()				
				+ " error : " + exception.getLocalizedMessage());
	}

}
