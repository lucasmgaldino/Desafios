package com.amaro.cache;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmaroCacheListener implements CacheEventListener<String, String> {

	private static final Logger log = LoggerFactory.getLogger(AmaroCacheListener.class);

	@Override
	public void onEvent(CacheEvent<? extends String, ? extends String> cacheEvent) {
		log.info("Event '{}' fired for key '{}' with value {}", cacheEvent.getType(), cacheEvent.getKey(),
				cacheEvent.getNewValue());
	}

}
