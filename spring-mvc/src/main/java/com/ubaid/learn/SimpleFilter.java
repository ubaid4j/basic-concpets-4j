package com.ubaid.learn;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleFilter extends HttpFilter {

    private static final Logger logger = LoggerFactory.getLogger(SimpleFilter.class);
    
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
        throws IOException, ServletException {
        logger.debug("################# This is Simple Filter before delegating to filter chain");
        chain.doFilter(req, res);
        logger.debug("################# This is Simple Filter after delegating to filter chain");
    }
}
