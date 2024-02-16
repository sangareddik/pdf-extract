package com.broadridge.mbse.pdfextract.controller;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping({GlobalErrorController.ERROR_PATH})
public class GlobalErrorController extends AbstractErrorController {
 
    protected static final String ERROR_PATH = "/error";
	private static final Logger logger = LoggerFactory.getLogger(GlobalErrorController.class);

    public GlobalErrorController(final ErrorAttributes errorAttributes) {
        super(errorAttributes, Collections.emptyList());
    }
 
    @GetMapping
    @PostMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
    	
        Map<String, Object> body = this.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        HttpStatus status = this.getStatus(request);
		logger.error("Request processing failed, URI {}, Description : {}", request.getRequestURI(), body);
        return new ResponseEntity<>(body, status);
    }
 
    public String getErrorPath() {
        return ERROR_PATH;
    }
}