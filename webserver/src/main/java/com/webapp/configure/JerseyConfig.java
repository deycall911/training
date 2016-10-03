package com.webapp.configure;

import com.sun.jersey.api.container.filter.LoggingFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.RequestContextFilter;

@Configuration
public class JerseyConfig extends ResourceConfig {

    private final static Logger LOG = LogManager.getLogger(JerseyConfig.class);

    public JerseyConfig() {
        LOG.trace("Jersey config");
//        register(RequestContextFilter.class);
//        packages("com.webapp");
//        property(ServletProperties.FILTER_FORWARD_ON_404, true);
//        register(LoggingFilter.class);
//        register(JspMvcFeature.class);
//        property(JspMvcFeature.TEMPLATES_BASE_PATH, "resources");
    }
}