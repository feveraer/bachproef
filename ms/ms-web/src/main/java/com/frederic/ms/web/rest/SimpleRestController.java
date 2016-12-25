package com.frederic.ms.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanNamer;
import org.springframework.cloud.sleuth.TraceRunnable;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/")
public class SimpleRestController {

    private static final Logger logger = LoggerFactory.getLogger(SimpleRestController.class);
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Tracer tracer;

    @Autowired
    private SpanNamer spanNamer;

    @RequestMapping(method = RequestMethod.GET)
    public String get() throws ExecutionException, InterruptedException {
        Future future1 = executor.submit(new TraceRunnable(tracer, spanNamer, new Runnable() {
            @Override
            public void run() {
                requestService("http://ms-service-1:8080/rest/");
            }

            @Override
            public String toString() {
                return "get_service_1";
            }
        }));

        Future future2 = executor.submit(new TraceRunnable(tracer, spanNamer, new Runnable() {
            @Override
            public void run() {
                requestService("http://ms-service-2:8080/rest/");
            }

            @Override
            public String toString() {
                return "get_service_2";
            }
        }));

        Future future3 = executor.submit(new TraceRunnable(tracer, spanNamer, new Runnable() {
            @Override
            public void run() {
                requestService("http://ms-service-3:8080/rest/");
            }

            @Override
            public String toString() {
                return "get_service_3";
            }
        }));

        // async requests
//        ListenableFuture<ResponseEntity<String>> future1 =
//                asyncRestTemplate.exchange("http://ms-service-1:8080/rest/",
//                        HttpMethod.GET,
//                        null,
//                        String.class);
//        ListenableFuture<ResponseEntity<String>> future2 =
//                asyncRestTemplate.exchange("http://ms-service-2:8080/rest/",
//                        HttpMethod.GET,
//                        null,
//                        String.class);
//        ListenableFuture<ResponseEntity<String>> future3 =
//                asyncRestTemplate.exchange("http://ms-service-3:8080/rest/",
//                        HttpMethod.GET,
//                        null,
//                        String.class);

        // wait for results
        String service1Response = (String) future1.get();
        String service2Response = (String) future2.get();
        String service3Response = (String) future3.get();

        logger.info("ms-web: called service-1, service-2 and service-3");
        return service1Response + "\n" + service2Response + "\n" + service3Response;
    }

    private void requestService(String serviceUrl) {
        restTemplate.exchange(serviceUrl, HttpMethod.GET, null, String.class);
    }

}
