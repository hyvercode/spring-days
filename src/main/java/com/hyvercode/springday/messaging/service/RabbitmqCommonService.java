package com.hyvercode.springday.messaging.service;

import com.hyvercode.springday.config.CacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RabbitmqCommonService {

  @Autowired
  private RedisTemplate<String, Boolean> redisTemplateGenericBoolean;

  private String buildCacheKey(String idempotencyKey) {
    return String.join(
      CacheConfig.DELIMITER,
      CacheConfig.CACHE_NAME_RABBITMQ_IDEMPOTENCY,
      idempotencyKey
    );
  }

  public boolean isExistsOnCache(String idempotencyKey) {
    return Optional
      .ofNullable(
        redisTemplateGenericBoolean.opsForValue().get(buildCacheKey(idempotencyKey))
      )
      .isPresent();
  }

  public void putOnCache(String idempotencyKey) {
    redisTemplateGenericBoolean.opsForValue().set(buildCacheKey(idempotencyKey), true);
  }

  public void removeFromCache(String idempotencyKey) {
    redisTemplateGenericBoolean
      .opsForValue()
      .getAndPersist(buildCacheKey(idempotencyKey));
  }
}
