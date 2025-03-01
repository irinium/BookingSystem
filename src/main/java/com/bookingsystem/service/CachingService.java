package com.bookingsystem.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.CacheStatistics;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class CachingService
{
   // 600000 = 10 minutes
   // 86400000 = 24 hours
   // 14400000 = 4 hours
   private static final long DEFAULT_CACHE_EXPIRY_TIME = 600000;

   private final CacheManager cacheManager;

   public void evictCaches(final String... caches)
   {
      for (String cache : caches)
      {
         evictCache(cache);
      }
   }

   private void evictCache(final String serviceCache)
   {
      logCacheStatistics();
      logCacheEviction(serviceCache);
      Cache cache = cacheManager.getCache(serviceCache);
      if (cache != null)
      {
         cache.clear();
      }
   }

   private void logCacheEviction(String serviceCache)
   {
      log.info("Evicting cache: [" + serviceCache + "]");
   }

   private void logCacheStatistics()
   {
      cacheManager.getCacheNames().forEach(this::dumpCacheStatistics);

   }

   private void dumpCacheStatistics(final String cacheName)
   {
      try
      {
         final RedisCache redisCache = (RedisCache) cacheManager.getCache(cacheName);
         final CacheStatistics cache = redisCache.getStatistics();
         log.info("Statistics for cache {} : puts - {}, gets - {}, deletes - {}, hits - {}, misses - {}", cacheName,
               cache.getPuts(),
               cache.getGets(),
               cache.getDeletes(),
               cache.getHits(),
               cache.getMisses());
      }
      catch (Exception e)
      {
         log.warn(e.getMessage());
      }
   }
}
