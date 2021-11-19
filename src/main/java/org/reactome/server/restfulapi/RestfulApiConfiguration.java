package org.reactome.server.restfulapi;

import org.gk.persistence.MySQLAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class RestfulApiConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(RestfulApiConfiguration.class);

    @Value("${mysql.host}")
    String mysqlHost;

    @Value("${mysql.db}")
    String mysqlDatabase;

    @Value("${mysql.user}")
    String mysqlUser;

    @Value("${mysql.pwd}")
    String mysqlPassword;
    
    @Bean
    public MySQLAdaptor getReactomeAnalysisConfig(@Value("${mysql.host}") String mysqlHost,
                                                  @Value("${mysql.db}") String mysqlDatabase,
                                                  @Value("${mysql.user}") String mysqlUser,
                                                  @Value("${mysql.pwd}") String mysqlPassword) {
        try {
            MySQLAdaptor dba = new MySQLAdaptor(mysqlHost, mysqlDatabase, mysqlUser, mysqlPassword);
            logger.info("Starting a dumb thread to keep MySQLAdaptor connected to avoid reconnection exception.");
            dba.initDumbThreadForConnection(); // To keep the DBA running to avoid connection error
            return dba;
        }
        catch(Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

}
