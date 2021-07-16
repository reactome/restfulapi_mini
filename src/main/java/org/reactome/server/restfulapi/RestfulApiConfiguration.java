package org.reactome.server.restfulapi;

import org.gk.persistence.MySQLAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class RestfulApiConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(RestfulApiConfiguration.class);
    
    @Autowired
    private Environment env;
    
    @Bean
    public MySQLAdaptor getReactomeAnalysisConfig() {
        try {
            MySQLAdaptor dba = new MySQLAdaptor(env.getProperty("mysql.host"), 
                                                env.getProperty("mysql.db"), 
                                                env.getProperty("mysql.user"), 
                                                env.getProperty("mysql.pwd"));
            return dba;
        }
        catch(Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

}
