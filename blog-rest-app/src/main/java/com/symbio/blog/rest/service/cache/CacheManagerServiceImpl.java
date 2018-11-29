package com.symbio.blog.rest.service.cache;

import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CacheManagerServiceImpl implements CacheManagerService {

    private static final Logger LOG = LoggerFactory.getLogger(CacheManagerServiceImpl.class);

    private static final String CACHE_NAME = "BLOG_CACHE";

    @Autowired
    private HazelcastInstance blogHazelcastInstance;

    public void putIntoCache(String key, Object value) {
        LOG.debug("Cache object with key [{}]", key.toLowerCase());
        if (value == null) {
            return;
        }
        Map<String, Object> hzMap = initHazelcastMap();
        hzMap.put(key.toLowerCase(), value);
    }


    @Override
    public Object getFromCache(String key) {
        LOG.debug("Get cached object with key [{}]", key);
        Map<String, Object> hzMap = initHazelcastMap();
        Object obj = hzMap.get(key.toLowerCase());
        return obj;
    }

    private Map<String, Object> initHazelcastMap() {
        Map<String, Object> acsMap = blogHazelcastInstance.getMap(CACHE_NAME);
        return acsMap;
    }

}
