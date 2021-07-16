package org.reactome.server.restfulapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// Have to extend this class for tomcat. See https://www.baeldung.com/spring-boot-war-tomcat-deploy.
@SpringBootApplication
public class RestfulapiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RestfulapiApplication.class, args);
	}

}
