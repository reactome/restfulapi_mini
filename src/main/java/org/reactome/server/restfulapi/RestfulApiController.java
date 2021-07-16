package org.reactome.server.restfulapi;

import java.io.StringWriter;

import org.gk.model.GKInstance;
import org.gk.model.InstanceNotFoundException;
import org.gk.persistence.MySQLAdaptor;
import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.reactome.biopax.ReactomeToBioPAX3XMLConverter;
import org.reactome.biopax.ReactomeToBioPAXXMLConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestfulApiController {
    private static Logger logger = LoggerFactory.getLogger(RestfulApiController.class);
    @Autowired
    private MySQLAdaptor dba;

    /**
     * BioPAX exporter
     * @param dbId Pathway Id
     * @return BioPAX model in OWL format
     */
    @GetMapping("/RESTfulWS/biopaxExporter/{level}/{dbId}")
    public String bioPaxExporter(@PathVariable("level") String level,
                                 @PathVariable("dbId") long dbId) {
        if (dba == null) {
            logger.error("No database is specified for biopax export.");
            return "No database is configured at the server side.";
        }
        try {
            GKInstance event = dba.fetchInstance(dbId);
            if (event == null)
                throw new InstanceNotFoundException(dbId);
            if (!event.getSchemClass().isa("Event")) {
                throw new IllegalStateException("The specified Instance is not an Event instance. Only Event instance can be exported.");
            }
            Document document = null;
            if (level.equalsIgnoreCase("Level2")) {
                ReactomeToBioPAXXMLConverter converter = new ReactomeToBioPAXXMLConverter();
                converter.setReactomeEvent(event);
                converter.convert();
                document = converter.getBioPAXModel();
            }
            else { // default should be the level 3, the current version
                ReactomeToBioPAX3XMLConverter converter = new ReactomeToBioPAX3XMLConverter();
                converter.setReactomeEvent(event);
                converter.convert();
                document = converter.getBioPAXModel();
            }
            XMLOutputter outputter = new XMLOutputter();
            outputter.setFormat(Format.getPrettyFormat());
            StringWriter writer = new StringWriter();
            outputter.output(document, writer);
            return writer.toString();
        } 
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "Error in biopax exporter: " + e.getMessage();
        }
    }

    /**
     * Get the database name preconfigued in the RESTful API.
     * @return
     */
    @GetMapping("/RESTfulWS/getDBName")
    public String getDBName() {
        if (this.dba == null)
            return null;
        return this.dba.getDBName();
    }

    /**
     * Returns the release version where the RESTFul instance is pointing to
     * @return the release version where the RESTFul instance is pointing to
     */
    @GetMapping("/RESTfulWS/version")
    public String getVersion() {
        if (this.dba == null)
            return null;
        try {
            return this.dba.getReleaseNumber().toString();
        }
        catch(Exception e) {
            logger.error(e.getMessage(), e);
            return "Error in get version: " + e.getMessage();
        }
    }

}
