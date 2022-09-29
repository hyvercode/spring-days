package com.hyvercode.springday.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyvercode.springday.component.HeaderRequestInterceptor;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class FeignClientConfig {


  public static final String AUTHORIZATION_HEADER_KEY = "Authorization";

  private final HeaderRequestInterceptor headerRequestInterceptor;

  public FeignClientConfig(HeaderRequestInterceptor headerRequestInterceptor) {
    this.headerRequestInterceptor = headerRequestInterceptor;
  }

  /**
   * Enable this bean if you want to add headers in HTTP request
   */
  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      requestTemplate.header("Content-Type", "application/json");
      requestTemplate.header("Accept", "application/json");
    };
  }

  @Bean
  public HeaderRequestInterceptor requestInterceptor(RequestTemplate requestTemplate) {
    return headerRequestInterceptor;
  }

  @Bean
  public Decoder feignDecoder() {
    HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter(customObjectMapper());

    HttpMessageConverters httpMessageConverters = new HttpMessageConverters(jacksonConverter);
    ObjectFactory<HttpMessageConverters> objectFactory = () -> httpMessageConverters;


    return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
  }

  public ObjectMapper customObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    //Customize as much as you want
    return objectMapper;
  }

}
