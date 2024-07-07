package com.hyvercode.springday.config;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.time.Duration;

@Configuration
public class CacheConfig {
  public static final String CACHE_NAME_OAUTH_TOKEN = "cache-oauth-token";
  public static final String CACHE_NAME_RABBITMQ_IDEMPOTENCY = "cache-rabbitmq-idempotency";
  public static final String DELIMITER = "::";

  @Bean
  RedisTemplate<String, Boolean> redisTemplateGenericBoolean(
    RedisConnectionFactory connectionFactory
  ) {
    var template = new RedisTemplate<String, Boolean>();
    template.setConnectionFactory(connectionFactory);
    template.setKeySerializer(new GenericJackson2JsonRedisSerializer());
    template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    return template;
  }

  @Bean
  RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
    return builder ->
      builder.withCacheConfiguration(
        CACHE_NAME_OAUTH_TOKEN,
        RedisCacheConfiguration
          .defaultCacheConfig()
          .disableCachingNullValues()
          .entryTtl(Duration.ofHours(23))
      );
  }
}
