package com.webapp.configure;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.RequestContextFilter;

@Configuration
public class FrontEndJerseyConfig extends ResourceConfig {

    private final static Logger LOG = LogManager.getLogger(FrontEndJerseyConfig.class);

    public FrontEndJerseyConfig() {
        LOG.trace("Jersey config");
        register(RequestContextFilter.class);
        packages("com.webapp.home");
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
    }
}