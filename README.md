# restfulapi_mini

To support Java 11, tomcat 9 and Spring boot, [the minimal version of the Reactome RESTful API](https://github.com/reactome/RESTfulAPI/tree/minimized) has been re-implemented. There are only three methods supported in this version. This version is used to provide biopax export for the Reactome production server. 

For all other functions that have been used at curator.reactome.org, see the original codebase: [https://github.com/reactome/RESTfulAPI](https://github.com/reactome/RESTfulAPI).

To deploy this SpringBoot application to tomcat, please follow the instruction in this web page: https://www.baeldung.com/spring-boot-war-tomcat-deploy. Note: Code in this repo has been updated according to this instruction. What you need to do is to run 'mvn package' after enter the correct mysql database information in application.properties. If you are using the command shell, make sure your default Java is 11 or the Java used in mvn in 11.
