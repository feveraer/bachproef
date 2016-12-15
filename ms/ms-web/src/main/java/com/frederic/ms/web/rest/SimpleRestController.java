package com.frederic.ms.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class SimpleRestController {

    private static final Logger logger = LoggerFactory.getLogger(SimpleRestController.class);

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        ResponseEntity<String> service1Response =
                restTemplate.exchange("http://ms-service-1:8080/rest/",
                        HttpMethod.GET,
                        null,
                        String.class);
        ResponseEntity<String> service2Response =
                restTemplate.exchange("http://ms-service-2:8080/rest/",
                        HttpMethod.GET,
                        null,
                        String.class);
        logger.info("ms-web: called service-1 and service-2");
        return service1Response.getBody() + "\n" + service2Response.getBody();
    }

}
