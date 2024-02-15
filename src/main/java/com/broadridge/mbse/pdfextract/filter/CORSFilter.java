package com.broadridge.mbse.pdfextract.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CORSFilter implements Filter {
  private static final String ONE_HOUR = "3600";
  
  private Logger logger = LoggerFactory.getLogger(CORSFilter.class);

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) res;
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
    response.setHeader("Access-Control-Max-Age", ONE_HOUR);
    response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Origin,Content-Type, Accept, x-device-user-agent, Content-Type");

    if (req instanceof HttpServletRequest) {
       HttpServletRequest httpServletRequest = (HttpServletRequest) req;
       if (httpServletRequest.getHeader(HttpHeaders.ORIGIN) != null
          && httpServletRequest.getMethod().equals(HttpMethod.OPTIONS.name())
          && httpServletRequest.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD) != null) {
    	  logger.debug("Received an OPTIONS pre-flight request.");
          return;
       }
    }
    chain.doFilter(req, res);
  }

  @Override
  public void destroy() {
  }


}