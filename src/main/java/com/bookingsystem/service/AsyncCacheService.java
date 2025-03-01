package com.bookingsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AsyncCacheService
{
   private final CachingService cachingService;

   @Async
   public void evictCaches(final String...caches) { cachingService.evictCaches(caches); }
}
