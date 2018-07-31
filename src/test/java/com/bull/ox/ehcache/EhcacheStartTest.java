package com.bull.ox.ehcache;

import org.apache.catalina.loader.ResourceEntry;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.CachePersistenceException;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.xml.XmlConfiguration;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class EhcacheStartTest {

    @Test
    public void testSetupByProgrammatic() {

        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("defaultCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(3))).build();
        cacheManager.init();
        Cache<String, String> defaultCache = cacheManager.getCache("defaultCache", String.class, String.class);
        defaultCache.put("key1", "value1");

        String value1 = defaultCache.get("key1");
        Assert.assertEquals("value1", value1);

        cacheManager.removeCache("defaultCache");
        Assert.assertNotNull(defaultCache);

        Cache<String, String> defaultCache2 = cacheManager.getCache("defaultCache", String.class, String.class);
        Assert.assertNull(defaultCache2);

        cacheManager.close();
    }

    @Test
    public void testSetupByXML() {
        CacheManager cacheManager = CacheManagerBuilder.newCacheManager(new XmlConfiguration(getClass().getResource("/ehcache.xml")));
        Assert.assertNotNull(cacheManager);
        cacheManager.init();

        Cache<String, String> defaultCache = cacheManager.getCache("foo", String.class, String.class);
        Assert.assertNotNull(defaultCache);
    }

    @Test
    public void testStorageTiers(){
        PersistentCacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence("d:/cache/temp"))
                .withCache("myDefaultCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder().heap(100L, EntryUnit.ENTRIES)
                                .offheap(20L, MemoryUnit.MB)
                                .disk(500L, MemoryUnit.MB, true))
                        .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(java.time.Duration.ofSeconds(1800L))))
                .build();
        cacheManager.init();

        cacheManager.close();
    }
}
