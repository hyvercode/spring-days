package com.hyvercode.springday.feign.config;


import com.hyvercode.springday.feign.api.BackofficeClientApi;
import com.hyvercode.springday.feign.api.FeignClientApi;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({FeignClientsConfiguration.class})
public class ApiFeignClientConfig {

  private final Encoder encoder;

  private final Decoder decoder;

  @Value("${feign.api.bfi-treasury-auth-api.baseUrl}")
  private String bfiTreasuryAuthApiBaseUrl;

  @Value("${feign.api.bfi-treasury-auth-api.key}")
  private String bfiTreasuryAuthApiKey;


  @Value("${feign.api.bfi-treasury-backoffice-api.baseUrl}")
  private String bfiTreasuryBackofficeApiBaseUrl;

  @Value("${feign.api.bfi-treasury-backoffice-api.key}")
  private String bfiTreasuryBackofficeApiKey;

  public ApiFeignClientConfig(Encoder encoder, Decoder decoder) {
    this.encoder = encoder;
    this.decoder = decoder;
  }


  @Bean
  public FeignClientApi feignClientApi() {
    return Feign.builder()
      .requestInterceptor(interceptor -> {
        // Set Auth related headers
        interceptor.header("api-secret", bfiTreasuryAuthApiKey);
      })
      .encoder(encoder)
      .decoder(decoder)
      .errorDecoder((methodKey, response) -> {
        val defaultErrorEncoder = new ErrorDecoder.Default();
        // Handle specific exception
        return defaultErrorEncoder.decode(methodKey, response);
      }) // Set the appropriate url
      .target(FeignClientApi.class, bfiTreasuryAuthApiBaseUrl);
  }

  @Bean
  public BackofficeClientApi backofficeClientApi() {
    return Feign.builder()
      .requestInterceptor(interceptor -> {
        // Set Auth related headers
        interceptor.header("api-secret", bfiTreasuryBackofficeApiKey);
      })
      .encoder(encoder)
      .decoder(decoder)
      .errorDecoder((methodKey, response) -> {
        val defaultErrorEncoder = new ErrorDecoder.Default();
        // Handle specific exception
        return defaultErrorEncoder.decode(methodKey, response);
      }) // Set the appropriate url
      .target(BackofficeClientApi.class, bfiTreasuryBackofficeApiBaseUrl);
  }

}
