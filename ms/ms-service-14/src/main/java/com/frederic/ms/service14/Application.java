package com.frederic.ms.service14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
@EnableDiscoveryClient
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // Sleuth
    @Bean
    public Sampler percentageSampler() {
        return new Sampler() {
            @Override
            public boolean isSampled(Span span) {Random rg = new Random();
                // trace 10% of all requests
                return rg.nextInt(10) > 8;
            }
        };
    }

   /* @Bean
    public AlwaysSampler defaultSampler() {
        return new AlwaysSampler();
    }*/
}
