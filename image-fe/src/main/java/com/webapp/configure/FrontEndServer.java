package com.webapp.configure;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class FrontEndServer {

    private final static Logger LOG = LogManager.getLogger(FrontEndServer.class);

    public static void main(String[] args) {
        LOG.trace("Server start");

        SpringApplication.run(FrontEndServer.class, args);
    }

    @Bean
    public ServletRegistrationBean jerseyServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/configure/*");
        registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, FrontEndJerseyConfig.class.getName());
        return registration;
    }


}
