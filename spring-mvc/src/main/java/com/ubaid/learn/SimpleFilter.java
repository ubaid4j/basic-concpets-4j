package com.ubaid.learn;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
