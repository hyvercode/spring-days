package com.hyvercode.springday;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableCaching
@EnableBatchProcessing
@EnableAsync
@RestController
public class SpringDayApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringDayApplication.class, args);
	}

  @GetMapping(value = "/")
  public String root(){
    return """
          <body>
              <div style="text-align:center; width:400px; margin:0 auto; padding: 5px;font-size:20px;">
                  Spring Days
              </div>
          </body>
      """;
  }
}
