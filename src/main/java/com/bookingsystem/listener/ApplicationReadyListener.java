package com.bookingsystem.listener;


import com.bookingsystem.service.AsyncCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@Order(0)
public class ApplicationReadyListener
      implements ApplicationListener<ApplicationReadyEvent>
{

   private final AsyncCacheService asyncCacheService;

   public ApplicationReadyListener(AsyncCacheService asyncCacheService)
   {
      this.asyncCacheService = asyncCacheService;
   }

   @Override
   public void onApplicationEvent(ApplicationReadyEvent event)
   {

      asyncCacheService.evictCaches("availableUnits");
      log.info("Application ready event processed.");
   }
}
