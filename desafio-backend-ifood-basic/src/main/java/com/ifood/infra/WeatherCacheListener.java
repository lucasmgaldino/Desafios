package com.ifood.infra;

import com.ifood.models.WeatherResponse;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherCacheListener implements CacheEventListener<String, WeatherResponse> {

    private static final Logger log = LoggerFactory.getLogger(WeatherCacheListener.class);

    @Override
    public void onEvent(CacheEvent<? extends String, ? extends WeatherResponse> cacheEvent) {
        log.info("Event '{}' fired for key '{}' with value {}", cacheEvent.getType(), cacheEvent.getKey(),
                cacheEvent.getNewValue());
    }
}
