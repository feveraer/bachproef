package com.frederic.ms.service8.rest;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.SpanNamer;
import org.springframework.cloud.sleuth.TraceCallable;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/rest")
public class SimpleRestController {

    private static final Logger logger = LoggerFactory.getLogger(SimpleRestController.class);
    private static final ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    @Value("${spring.application.name}")
    private String serviceName;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Tracer tracer;

    @Autowired
    private SpanNamer spanNamer;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String get() throws ExecutionException, InterruptedException {
        Callable<ResponseEntity<String>> callable1 = new Callable<ResponseEntity<String>>() {
            @Override
            public ResponseEntity<String> call() throws Exception {
                return requestService("http://ms-service-21:8080/rest/");
            }
        };
        Callable<ResponseEntity<String>> traceCallable1 = new TraceCallable<>
                (tracer, spanNamer, callable1, "get_service_21");

        ListenableFuture<ResponseEntity<String>> future1 = executor.submit(traceCallable1);

        Callable<ResponseEntity<String>> callable2 = new Callable<ResponseEntity<String>>() {
            @Override
            public ResponseEntity<String> call() throws Exception {
                return requestService("http://ms-service-22:8080/rest/");
            }
        };
        Callable<ResponseEntity<String>> traceCallable2 = new TraceCallable<>
                (tracer, spanNamer, callable2, "get_service_22");

        ListenableFuture<ResponseEntity<String>> future2 = executor.submit(traceCallable2);
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
        ResponseEntity<String> service1Response = future1.get();
        ResponseEntity<String> service2Response = future2.get();

        logger.info(serviceName + ": called service-21 and service-22");
        StringBuilder sb = new StringBuilder(serviceName)
                .append(" [").append(service1Response.getBody()).append("]\n[")
                .append(service2Response.getBody()).append("]");
        return sb.toString();
    }

    private ResponseEntity<String> requestService(String serviceUrl) {
        return restTemplate.exchange(serviceUrl, HttpMethod.GET, null, String.class);
    }
}
