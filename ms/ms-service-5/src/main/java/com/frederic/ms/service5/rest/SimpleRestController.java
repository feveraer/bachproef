package com.frederic.ms.service5.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class SimpleRestController {

    private static final Logger logger = LoggerFactory.getLogger(SimpleRestController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String get() {
        logger.info("get() called");
        return "service-5: Hello world!";
    }

}
