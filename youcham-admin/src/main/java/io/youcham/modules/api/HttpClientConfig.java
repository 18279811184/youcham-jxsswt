package io.youcham.modules.api;

import com.netflix.hystrix.HystrixCommandProperties;
import feign.hystrix.HystrixFeign;
import feign.hystrix.SetterFactory;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

	@Value("${cas.user-center.url}")
    private String userCenterBaseUrl;

    @Bean
    UserCenterClient userCenterClient() throws InterruptedException {
        return HystrixFeign.builder()
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger(UserCenterClient.class))
                .encoder(new JacksonEncoder())
                .setterFactory((target, method) ->
                        new SetterFactory.Default().create(target, method).
                                andCommandPropertiesDefaults(HystrixCommandProperties.defaultSetter().
                                        withExecutionTimeoutInMilliseconds(100000)))
                .target(UserCenterClient.class, this.userCenterBaseUrl);
    }


}
