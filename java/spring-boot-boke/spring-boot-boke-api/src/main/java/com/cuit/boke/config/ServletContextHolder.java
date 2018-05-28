package com.cuit.boke.config;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

@Service
@Lazy(false)
public class ServletContextHolder implements ServletContextAware {
      
    private static ServletContext servletContext;

    public void setServletContext(ServletContext sc) {
        servletContext = sc;
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }
}