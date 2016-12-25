package com.frederic.ms.service2.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.SpanNamer;
import org.springframework.cloud.sleuth.TraceRunnable;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/rest")
public class SimpleRestController {

    private static final Logger logger = LoggerFactory.getLogger(SimpleRestController.class);
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Tracer tracer;

    @Autowired
    private SpanNamer spanNamer;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String get() throws ExecutionException, InterruptedException {
        Future future1 = executor.submit(new TraceRunnable(tracer, spanNamer, new Runnable() {
            @Override
            public void run() {
                requestService("http://ms-service-7:8080/rest/");
            }

            @Override
            public String toString() {
                return "get_service_7";
            }
        }));

        Future future2 = executor.submit(new TraceRunnable(tracer, spanNamer, new Runnable() {
            @Override
            public void run() {
                requestService("http://ms-service-8:8080/rest/");
            }

            @Override
            public String toString() {
                return "get_service_8";
            }
        }));
        // async requests
//        ListenableFuture<ResponseEntity<String>> future1 =
//                asyncRestTemplate.exchange("http://ms-service-4:8080/rest/",
//                        HttpMethod.GET,
//                        null,
//                        String.class);
//        ListenableFuture<ResponseEntity<String>> future2 =
//                asyncRestTemplate.exchange("http://ms-service-5:8080/rest/",
//                        HttpMethod.GET,
//                        null,
//                        String.class);

        // wait for results
        String service1Response = (String) future1.get();
        String service2Response = (String) future2.get();

        logger.info("ms-service-2: called service-7 and service-8");
        return service1Response + "\n" + service2Response;
    }

    private void requestService(String serviceUrl) {
        restTemplate.exchange(serviceUrl, HttpMethod.GET, null, String.class);
    }

}
