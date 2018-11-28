package com.symbio.blog.rest;

import net.sf.ehcache.management.ManagementService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;

//@Configuration("eh-cache-context")
//@EnableCaching
public class EHCacheContext {

    @Bean
    public CacheManager cacheManager() {
        EhCacheCacheManager cacheManager = new EhCacheCacheManager();
        cacheManager.setCacheManager(ehcache().getObject());
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ManagementService.registerMBeans(cacheManager.getCacheManager(), mBeanServer, true, true, true, true);
        return cacheManager;
    }

    @Bean
    public EhCacheManagerFactoryBean ehcache() {
        EhCacheManagerFactoryBean ehcache = new EhCacheManagerFactoryBean();
        Resource ehCacheConf = new ClassPathResource("blog-ehcache-conf.xml");
        ehcache.setShared(true);
        ehcache.setConfigLocation(ehCacheConf);
        return ehcache;
    }


}
