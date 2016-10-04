package com.db.configure;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.RequestContextFilter;


@Configuration
public class DataBaseJerseyConfig extends ResourceConfig {

    private final static Logger LOG = LogManager.getLogger(DataBaseJerseyConfig.class);

    public DataBaseJerseyConfig() {
        LOG.trace("Jersey config");
        register(RequestContextFilter.class);
        packages("com.db.requests");
        register(LoggingFilter.class);
        register(MultiPartFeature.class);
        register(new CORSFilter());
    }
}