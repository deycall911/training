package com.db.configure;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.db.repository")
@EntityScan("com.db.entity")
@SpringBootApplication
public class DataBaseServer {

    private final static Logger LOG = LogManager.getLogger(DataBaseServer.class);

    public static void main(String[] args) {
        LOG.trace("Server start");

        SpringApplication.run(DataBaseServer.class, args);
    }

    @Bean
    public ServletRegistrationBean jerseyServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/configure/*");
        registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, DataBaseJerseyConfig.class.getName());
        return registration;
    }


}
